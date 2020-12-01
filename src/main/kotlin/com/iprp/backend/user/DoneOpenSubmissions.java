package com.iprp.backend.user;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonAutoDetect
public class DoneOpenSubmissions {

    @JsonProperty
    private ArrayList<SubmissionID> done;
    @JsonProperty
    private ArrayList<SubmissionID> open;

    @JsonCreator
    public DoneOpenSubmissions(@JsonProperty("done") ArrayList<SubmissionID> done, @JsonProperty("open") ArrayList<SubmissionID> open) {
        this.done = done;
        this.open = open;
    }
}
