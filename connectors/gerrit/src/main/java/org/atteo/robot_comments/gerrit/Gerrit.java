package org.atteo.robot_comments.gerrit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.atteo.robot_comments.specification.Comment;
import org.atteo.robot_comments.specification.RobotReview;

import com.google.gerrit.extensions.api.changes.ReviewInput;
import com.google.gerrit.extensions.common.ChangeInfo;
import com.google.gerrit.extensions.restapi.RestApiException;
import com.urswolfer.gerrit.client.rest.GerritAuthData;
import com.urswolfer.gerrit.client.rest.GerritRestApi;
import com.urswolfer.gerrit.client.rest.GerritRestApiFactory;

import static java.util.stream.Collectors.groupingBy;

public class Gerrit {

    private final GerritRestApi gerritApi;

    public Gerrit(String gerritUrl, String user, String password) {
        var gerritRestApiFactory = new GerritRestApiFactory();
        var authData = new GerritAuthData.Basic(gerritUrl, user, password);
        gerritApi = gerritRestApiFactory.create(authData);
    }

    public void upload(String change, String revision, RobotReview robotReview) {
        var reviewInput = new ReviewInput();

        reviewInput.robotComments = robotReview.getComments().stream()
            .map(Gerrit::toGerritComment)
            .collect(groupingBy(Gerrit::getPath));

        try {
            gerritApi.changes().id(change).revision(revision).review(reviewInput);
        } catch (RestApiException e) {
            throw new RuntimeException(e);
        }
    }

    public static ReviewInput.RobotCommentInput toGerritComment(Comment comment) {
        var path = "Dockerfile";

        var gerritComment = new ReviewInput.RobotCommentInput();
        gerritComment.path = comment.getFile();
        gerritComment.message = comment.getMessage();
        gerritComment.robotId = "spotbugs";
        gerritComment.robotRunId = "1";

        return gerritComment;
    }

    public static String getPath(ReviewInput.RobotCommentInput comment) {
        return comment.path;
    }
}
