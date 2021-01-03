package com.iprp.backend.controller.obj;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ReviewID {

    @JsonProperty
    private String sid;
    @JsonProperty
    private String from, to;


    @JsonCreator
    public ReviewID(@JsonProperty("sid") String sid,@JsonProperty("from") String from,@JsonProperty("to") String to) {
        this.sid = sid;
        this.from = from;
        this.to = to;
    }
}
