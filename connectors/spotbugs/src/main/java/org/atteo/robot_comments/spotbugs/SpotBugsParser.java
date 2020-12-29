package org.atteo.robot_comments.spotbugs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.stream.Collectors;

import org.atteo.robot_comments.specification.Comment;
import org.atteo.robot_comments.specification.RobotReview;
import org.atteo.robot_comments.specification.Side;
import org.atteo.robot_comments.spotbugs.xml.BugCollection;
import org.atteo.robot_comments.spotbugs.xml.Project;
import org.atteo.robot_comments.spotbugs.xml.SourceLine;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import static java.lang.Integer.min;

public class SpotBugsParser {
    public RobotReview parse(String path) throws IOException {

        try (var inputStream = new FileInputStream(path)) {
            return parse(inputStream);
        }
    }

    public RobotReview parse(InputStream inputStream) throws IOException {
        var mapper = new XmlMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        var bugCollection = mapper.readValue(inputStream, BugCollection.class);

        return convert(bugCollection);
    }

    private RobotReview convert(BugCollection bugCollection) {
        var prefix = calculateSourcePrefix(bugCollection.getProject()) + "/";

        var comments = bugCollection.getBugInstances().stream()
            .map(this::getComment)
            .map(comment -> comment.addPathPrefix(prefix))
            .collect(Collectors.toList());

        return new RobotReview(comments);
    }

    private Comment getComment(org.atteo.robot_comments.spotbugs.xml.BugInstance bugInstance) {
        var sourceLine = bugInstance.getSourceLine();

        // only output startLine if differs from endLine
        var startLine = Objects.equals(sourceLine.getStart(), sourceLine.getEnd()) ? null : sourceLine.getStart();

        return new Comment(
            sourceLine.getSourcepath(),
            startLine,
            sourceLine.getEnd(),
            bugInstance.getLongMessage(),
            Side.REVISION);
    }

    private String calculateSourcePrefix(Project project) {
        int firstDifferencePosition = firstDifference(project.getSrcDir(), project.getWrkDir());

        return project.getSrcDir().substring(firstDifferencePosition);
    }

    private static int firstDifference(String first, String second) {
        int length = min(first.length(), second.length());

        for (int i = 0; i < length; i++) {
            if (first.charAt(i) != second.charAt(i)) {
                return i;
            }
        }
        return length;
    }
}
