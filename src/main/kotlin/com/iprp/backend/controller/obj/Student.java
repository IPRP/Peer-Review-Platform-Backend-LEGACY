package com.iprp.backend.controller.obj;

import com.fasterxml.jackson.annotation.*;

@JsonAutoDetect
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Student {
    @JsonProperty
    private String id, firstname, lastname, group;

    @JsonCreator
    public Student(@JsonProperty("id") String id, @JsonProperty("firstname") String firstname, @JsonProperty("lastname") String lastname, @JsonProperty("group") String group) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.group = group;
    }

    @JsonGetter
    public com.iprp.backend.data.user.Student getKotlinStudent(){
        return new com.iprp.backend.data.user.Student(this.id, this.firstname, this.lastname, this.group);
    }

    @JsonGetter
    public String getId() {
        return id;
    }
    @JsonSetter
    public void setId(String id) {
        this.id = id;
    }
    @JsonGetter
    public String getFirstname() {
        return firstname;
    }
    @JsonSetter
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }
    @JsonGetter
    public String getLastname() {
        return lastname;
    }
    @JsonSetter
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    @JsonGetter
    public String getGroup() {
        return group;
    }
    @JsonSetter
    public void setGroup(String group) {
        this.group = group;
    }
}
