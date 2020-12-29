package org.atteo.robot_comments.spotbugs.xml;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BugInstance {
    @JsonProperty("ShortMessage")
    private String shortMessage;

    @JsonProperty("LongMessage")
    private String longMessage;

    @JsonProperty("SourceLine")
    private SourceLine sourceLine;

    public String getShortMessage() {
        return shortMessage;
    }

    public String getLongMessage() {
        return longMessage;
    }

    public SourceLine getSourceLine() {
        return sourceLine;
    }
}
