package org.atteo.robot_comments.spotbugs.xml;

public class SourceLine {
    private String sourcepath;
    private Integer start;
    private Integer end;
    private String message;

    public String getSourcepath() {
        return sourcepath;
    }

    public Integer getStart() {
        return start;
    }

    public Integer getEnd() {
        return end;
    }

    public String getMessage() {
        return message;
    }
}
