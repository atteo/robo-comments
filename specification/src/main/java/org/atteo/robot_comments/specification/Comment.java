package org.atteo.robot_comments.specification;

public class Comment {
    private String file;
    private Integer line;
    private String message;

    public Comment(String file, Integer line, String message) {
        this.file = file;
        this.line = line;
        this.message = message;
    }

    public String getFile() {
        return file;
    }

    public Integer getLine() {
        return line;
    }

    public String getMessage() {
        return message;
    }
}
