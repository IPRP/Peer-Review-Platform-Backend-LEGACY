package com.iprp.backend.controller.obj;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.ArrayList;

@JsonAutoDetect
public class Workshop {

    @JsonProperty
    private String id;
    @JsonProperty
    private boolean anonym;
    @JsonProperty
    private String title, beschreibung, deadline;
    @JsonDeserialize(as = ArrayList.class, contentAs = String.class)
    private ArrayList<String> members;
    @JsonDeserialize(as = ArrayList.class, contentAs = DoneOpenSubmissions.class)
    private ArrayList<DoneOpenSubmissions> submissions;
    @JsonDeserialize(as = ArrayList.class, contentAs = DoneOpenReviews.class)
    private ArrayList<DoneOpenReviews> reviews;
    @JsonDeserialize(as = ArrayList.class, contentAs = Kriterium.class)
    private ArrayList<Kriterium> kriterien;

    @JsonCreator
    public Workshop(@JsonProperty("id") String id, @JsonProperty("title") String title,@JsonProperty("beschreibung") String beschreibung,@JsonProperty("deadline") String deadline,@JsonProperty("anonym") boolean anonym, @JsonProperty("members") ArrayList<String> members, @JsonProperty("submissions") ArrayList<DoneOpenSubmissions> submissions, @JsonProperty("reviews") ArrayList<DoneOpenReviews> reviews, @JsonProperty("kriterien") ArrayList<Kriterium> kriterien) {
        this.title = title;
        this.members = members;
        this.submissions = submissions;
        this.reviews = reviews;
        this.id = id;
        this.beschreibung = beschreibung;
        this.deadline = deadline;
        this.anonym = anonym;
        this.kriterien = kriterien;
        System.out.println(kriterien.toArray().toString());
    }

    @JsonGetter
    public ArrayList<Kriterium> getKriterien() {
        return kriterien;
    }
    @JsonSetter
    public void setKriterien(ArrayList<Kriterium> kriterien) {
        this.kriterien = kriterien;
    }
    @JsonGetter
    public boolean isAnonym() {
        return anonym;
    }
    @JsonSetter
    public void setAnonym(boolean anonym) {
        this.anonym = anonym;
    }

    @JsonGetter
    public String getDeadline() {
        return deadline;
    }
    @JsonSetter
    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

    @JsonGetter
    public String getBeschreibung() {
        return beschreibung;
    }

    @JsonSetter
    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
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
