package org.atteo.robot_comments.gerrit;

import java.time.LocalDateTime;

import org.atteo.robot_comments.specification.Comment;
import org.atteo.robot_comments.specification.RobotReview;
import org.atteo.robot_comments.specification.Side;

import com.google.gerrit.extensions.api.changes.ReviewInput;
import com.google.gerrit.extensions.restapi.RestApiException;
import com.urswolfer.gerrit.client.rest.GerritAuthData;
import com.urswolfer.gerrit.client.rest.GerritRestApi;
import com.urswolfer.gerrit.client.rest.GerritRestApiFactory;

import static java.util.stream.Collectors.groupingBy;

public class Gerrit {

    private final GerritRestApi gerritApi;
    private final String robotRun;
    private final String user;

    public Gerrit(String gerritUrl, String user, String password) {
        var gerritRestApiFactory = new GerritRestApiFactory();
        var authData = new GerritAuthData.Basic(gerritUrl, user, password);
        this.user = user;
        gerritApi = gerritRestApiFactory.create(authData);
        robotRun = LocalDateTime.now().toString();
    }

    public void upload(String change, String revision, RobotReview robotReview) {
        var reviewInput = new ReviewInput();

        reviewInput.robotComments = robotReview.getComments().stream()
            .map(this::toGerritComment)
            .collect(groupingBy(Gerrit::getPath));

        try {
            gerritApi.changes().id(change).revision(revision).review(reviewInput);
        } catch (RestApiException e) {
            throw new RuntimeException(e);
        }
    }

    private ReviewInput.RobotCommentInput toGerritComment(Comment comment) {
        var gerritComment = new ReviewInput.RobotCommentInput();
        gerritComment.path = comment.getPath();
        gerritComment.line = comment.getLine();
        gerritComment.side = comment.getSide() == Side.REVISION ?
            com.google.gerrit.extensions.client.Side.REVISION : com.google.gerrit.extensions.client.Side.PARENT;
        gerritComment.message = comment.getMessage();
        gerritComment.robotId = user;
        gerritComment.robotRunId = robotRun;

        return gerritComment;
    }

    public static String getPath(ReviewInput.RobotCommentInput comment) {
        return comment.path;
    }
}
