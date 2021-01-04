package com.iprp.backend.controller.obj;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
public class SubmissionID {

    @JsonProperty
    private String rid;
    @JsonProperty
    private String name;
    @JsonProperty
    private boolean lated;

    @JsonCreator
    public SubmissionID(@JsonProperty("rid") String rid,@JsonProperty("name") String name,@JsonProperty("lated") boolean lated) {
        this.rid = rid;
        this.name = name;
        this.lated = lated;
    }


    public String getRid() {

        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isLated() {
        return lated;
    }

    public void setLated(boolean lated) {
        this.lated = lated;
    }
}
