package org.atteo.robot_comments.specification;

import java.util.List;

public class RobotReview {
    private List<Comment> comments;

    public RobotReview(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Comment> getComments() {
        return comments;
    }
}
