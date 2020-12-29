package org.atteo.robot_comments.spotbugs.xml;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

public class BugCollection {
    private String sequence;
    private String release;
    private String analysisTimestamp;
    private String version;
    private String timestamp;

    @JsonProperty("Project")
    private Project project;

    @JsonProperty("BugInstance")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<BugInstance> bugInstances = new ArrayList<>();

    public String getSequence() {
        return sequence;
    }

    public String getRelease() {
        return release;
    }

    public String getAnalysisTimestamp() {
        return analysisTimestamp;
    }

    public String getVersion() {
        return version;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public List<BugInstance> getBugInstances() {
        return bugInstances;
    }

    public Project getProject() {
        return project;
    }
}
