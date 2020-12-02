package com.iprp.backend.user;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.ArrayList;

@JsonAutoDetect
public class NewWorkshop {

    @JsonProperty
    private int id;
    @JsonProperty
    private String title;
    @JsonDeserialize(as = ArrayList.class, contentAs = String.class)
    private ArrayList<String> members;
    @JsonDeserialize(as = ArrayList.class, contentAs = DoneOpenSubmissions.class)
    private ArrayList<DoneOpenSubmissions> submissions;
    @JsonDeserialize(as = ArrayList.class, contentAs = DoneOpenReviews.class)
    private ArrayList<DoneOpenReviews> reviews;


    @JsonCreator
    public NewWorkshop(@JsonProperty("id") int id, @JsonProperty("title") String title, @JsonProperty("members") ArrayList<String> members, @JsonProperty("submissions") ArrayList<DoneOpenSubmissions> submissions, @JsonProperty("reviews") ArrayList<DoneOpenReviews> reviews) {
        this.title = title;
        this.members = members;
        this.submissions = submissions;
        this.reviews = reviews;
        this.id = id;
    }


    @JsonGetter
    public int getId() {
        return id;
    }

    @JsonSetter
    public void setId(int id) {
        this.id = id;
    }

    @JsonGetter
    public String getTitle() {
        return title;
    }

    @JsonSetter
    public void setTitle(String title) {
        this.title = title;
    }

    @JsonGetter
    public ArrayList<String> getMembers() {
        return members;
    }

    @JsonSetter
    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }

    @JsonGetter
    public ArrayList<DoneOpenSubmissions> getSubmissions() {
        return submissions;
    }

    @JsonSetter
    public void setSubmissions(ArrayList<DoneOpenSubmissions> submissions) {
        this.submissions = submissions;
    }

    @JsonGetter
    public ArrayList<DoneOpenReviews> getReviews() {
        return reviews;
    }
    @JsonSetter
    public void setReviews(ArrayList<DoneOpenReviews> reviews) {
        this.reviews = reviews;
    }
}
