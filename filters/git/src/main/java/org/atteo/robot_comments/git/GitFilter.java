package org.atteo.robot_comments.git;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashSet;

import org.atteo.robot_comments.specification.RobotReview;
import org.atteo.robot_comments.specification.Side;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.util.io.DisabledOutputStream;

import static java.util.stream.Collectors.toList;

public class GitFilter {
    public RobotReview filter(RobotReview robotReview) throws IOException {

        var parentFiles = new HashSet<String>();
        var revisionFiles = new HashSet<String>();

        getChangesFiles(parentFiles, revisionFiles);

        var comments = robotReview.getComments().stream()
            .filter(comment -> {
                if (comment.getSide() == Side.REVISION) {
                    return revisionFiles.contains(comment.getPath());
                } else {
                    return parentFiles.contains(comment.getPath());
                }
            }).collect(toList());

        return new RobotReview(comments);
    }

    private void getChangesFiles(HashSet<String> parentFiles, HashSet<String> revisionFiles) throws IOException {
        try(var git = Git.open(Paths.get("../..").toFile())) {
            var reader = git.getRepository().newObjectReader();
            var oldTreeIter = new CanonicalTreeParser();
            var newTreeIter = new CanonicalTreeParser();

            var oldTree = git.getRepository().resolve("HEAD~1^{tree}");
            var newTree = git.getRepository().resolve("HEAD^{tree}");

            oldTreeIter.reset(reader, oldTree);
            newTreeIter.reset(reader, newTree);

            var diffFormatter = new DiffFormatter(DisabledOutputStream.INSTANCE);
            diffFormatter.setRepository(git.getRepository());
            var entries = diffFormatter.scan(oldTreeIter, newTreeIter);

            for (DiffEntry entry : entries) {
                if (entry.getOldPath() != null) {
                    parentFiles.add(entry.getOldPath());
                }
                if (entry.getNewPath() != null) {
                    revisionFiles.add(entry.getNewPath());
                }
            }
        }
    }
}
