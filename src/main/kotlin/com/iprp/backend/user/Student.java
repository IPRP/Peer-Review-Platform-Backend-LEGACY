package com.iprp.backend.user;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect
public class Student extends Person{

    @JsonCreator
    public Student(@JsonProperty("id") String id,@JsonProperty("vorname") String vorname,@JsonProperty("nachname") String nachname){
        super(id, vorname, nachname);
    }
}
