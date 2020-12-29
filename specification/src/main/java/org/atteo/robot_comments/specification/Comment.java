package org.atteo.robot_comments.specification;

/**
 * Generic comment.
 *
 * @see <a href="https://gerrit-review.googlesource.com/Documentation/rest-api-changes.html#robot-comment-input">Gerrit RobotCommentInput structure</a>
 * @see <a href="https://docs.github.com/en/free-pro-team@latest/rest/reference/pulls#create-a-review-comment-for-a-pull-request">GitHub comment structure</a>
 */
public class Comment {
    /**
     * The relative path to the file that necessitates a comment. Required.
     */
    private String path;

    /**
     * The text of the review comment. Required.
     */
    private String message;

    /**
     * The line of the file that comment applies to. For a multi-line comment,
     * the last line of the range that your comment applies to. If not provided a file comment is added.
     */
    private Integer line;

    /**
     * For a multi-line comment the start line number of the range that your comment applies to. (1-based)
     */
    private Integer startLine;

    /**
     * The character position in the start line. (0-based)
     */
    private Integer startCharacter;

    /**
     * The character position in the end line. (0-based)
     */
    private Integer endCharacter;

    /**
     * In a split diff view, the side of the diff that the pull request's changes appear on.
     * Can be LEFT or RIGHT. Use LEFT for deletions that appear in red.
     * Use RIGHT for additions that appear in green or unchanged lines that appear in white and are shown for context.
     */
    private Side side;

    public Comment(String path, Integer startLine, Integer line, String message, Side side) {
        this.path = path;
        this.line = line;
        this.message = message;
        this.side = side;
    }

    public Comment(String path, Integer startLine, Integer line, Integer startCharacter, Integer endCharacter,
                   String message, Side side) {
        this.path = path;
        this.line = line;
        this.message = message;
        this.side = side;
    }

    public String getPath() {
        return path;
    }

    public Integer getLine() {
        return line;
    }

    public String getMessage() {
        return message;
    }

    public Side getSide() {
        return side;
    }

    public Integer getStartLine() {
        return startLine;
    }

    public Integer getStartCharacter() {
        return startCharacter;
    }

    public Integer getEndCharacter() {
        return endCharacter;
    }

    public Comment addPathPrefix(String prefix) {
        return new Comment(prefix + path, startLine, line, startCharacter, endCharacter, message, side);
    }
}
