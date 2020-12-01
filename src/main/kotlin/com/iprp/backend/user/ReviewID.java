package com.iprp.backend.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReviewID {

    @JsonProperty
    private int sid;
    @JsonProperty
    private String from, to;


    @JsonCreator
    public ReviewID(@JsonProperty("sid") int sid,@JsonProperty("from") String from,@JsonProperty("to") String to) {
        this.sid = sid;
        this.from = from;
        this.to = to;
    }
}
