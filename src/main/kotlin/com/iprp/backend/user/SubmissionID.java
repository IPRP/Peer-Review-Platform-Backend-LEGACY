package com.iprp.backend.user;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
public class SubmissionID {

    @JsonProperty
    private int rid;
    @JsonProperty
    private String name;
    @JsonProperty
    private boolean lated;

    @JsonCreator
    public SubmissionID(@JsonProperty("rid") int rid,@JsonProperty("name") String name,@JsonProperty("lated") boolean lated) {
        this.rid = rid;
        this.name = name;
        this.lated = lated;
    }
}
