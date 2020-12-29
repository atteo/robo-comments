package org.atteo.robot_comments.spotbugs;

import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Collectors;

import org.atteo.robot_comments.specification.Comment;
import org.atteo.robot_comments.specification.RobotReview;
import org.atteo.robot_comments.spotbugs.xml.BugCollection;
import org.atteo.robot_comments.spotbugs.xml.Project;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import static java.lang.Integer.min;

public class SpotBugsParser {
    public RobotReview parse(InputStream inputStream) throws IOException {
        var mapper = new XmlMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        var spotBugsRoot = mapper.readValue(inputStream, BugCollection.class);

        return convert(spotBugsRoot);
    }

    private RobotReview convert(BugCollection bugCollection) {
        var prefix = calculateSourcePrefix(bugCollection.getProject()) + "/";

        var comments = bugCollection.getBugInstances().stream()
            .map(bugInstance -> new Comment(
                prefix + bugInstance.getSourceLine().getSourcepath(),
                bugInstance.getSourceLine().getStart(),
                bugInstance.getLongMessage())
            ).collect(Collectors.toList());

        return new RobotReview(comments);
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
