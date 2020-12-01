package com.iprp.backend.user;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonAutoDetect
public class DoneOpenReviews {

    @JsonProperty
    private ArrayList<ReviewID> done;
    @JsonProperty
    private ArrayList<ReviewID> open;

    @JsonCreator
    public DoneOpenReviews(@JsonProperty("done") ArrayList<ReviewID> done, @JsonProperty("open") ArrayList<ReviewID> open) {
        this.done = done;
        this.open = open;
    }
}
