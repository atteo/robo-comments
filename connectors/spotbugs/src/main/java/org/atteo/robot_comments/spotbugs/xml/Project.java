package org.atteo.robot_comments.spotbugs.xml;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Project {
    @JsonProperty("SrcDir")
    private String srcDir;

    @JsonProperty("WrkDir")
    private String wrkDir;

    public String getSrcDir() {
        return srcDir;
    }

    public String getWrkDir() {
        return wrkDir;
    }
}
