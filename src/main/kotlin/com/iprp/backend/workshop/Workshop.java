package com.iprp.backend.workshop;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.iprp.backend.abgabe.Abgabe;
import com.iprp.backend.review.Review;
import com.iprp.backend.user.Student;

import java.util.ArrayList;

@JsonAutoDetect
public class Workshop {

    @JsonProperty
    private String name, id;
    @JsonDeserialize(as = ArrayList.class, contentAs = Student.class)
    private ArrayList<Student> teilnehmer;
    @JsonDeserialize(as = ArrayList.class, contentAs = Abgabe.class)
    private ArrayList<Abgabe> abgaben;
    @JsonDeserialize(as = ArrayList.class, contentAs = Review.class)
    private ArrayList<Review> reviews;


    public Workshop(String name) {
        this.name = name;
        this.id = name;
        this.teilnehmer = new ArrayList<Student>();
        this.abgaben = new ArrayList<Abgabe>();
        this.reviews = new ArrayList<Review>();
    }

    @JsonCreator
    public Workshop(@JsonProperty("name") String name,@JsonProperty("id") String id,@JsonProperty("teilnehmer") ArrayList<Student> teilnehmer,@JsonProperty("abgaben") ArrayList<Abgabe> abgaben,@JsonProperty("reviews") ArrayList<Review> reviews){
        this.name = name;
        this.id = id;
        this.teilnehmer = teilnehmer;
        this.abgaben = abgaben;
        this.reviews = reviews;
    }


    @JsonGetter("id")
    public String getId() {
        return id;
    }

    @JsonSetter("id")
    public void setId(String id) {
        this.id = id;
    }

    public void addTeilnehmer(Student teilnehmer){
        this.teilnehmer.add(teilnehmer);
    }

    public void delTeilnehmer(Student teilnehmer){
        this.teilnehmer.remove(teilnehmer);
    }

    public void editTeilnehmer(Student teilnehmerOld, Student teilnehmerNeu){
        this.delTeilnehmer(teilnehmerOld);
        this.addTeilnehmer(teilnehmerNeu);
    }
    public void addAbgabe(Abgabe abgabe){
        this.abgaben.add(abgabe);
    }

    public void delAbgabe(Abgabe abgabe){
        this.abgaben.remove(abgabe);
    }

    public void editAbgabe(Abgabe abgabeOld, Abgabe abgabeNeu){
        this.delAbgabe(abgabeOld);
        this.addAbgabe(abgabeNeu);
    }

    public void addReview(Review review){
        this.reviews.add(review);
    }

    public void delReview(Review review){
        this.reviews.remove(review);
    }

    public void editReview(Review reviewOld, Review reviewNeu){
        this.delReview(reviewOld);
        this.addReview(reviewNeu);
    }

    @JsonGetter("name")
    public String getName() {
        return name;
    }

    @JsonSetter("name")
    public void setName(String name) {
        this.name = name;
    }

    /*
    public ArrayList<Teacher> getLehrer() {
        return lehrer;
    }

    public void setLehrer(ArrayList<Teacher> lehrer) {
        this.lehrer = lehrer;
    }
    */

    @JsonGetter("teilnehmer")
    public ArrayList<Student> getTeilnehmer() {
        return teilnehmer;
    }

    @JsonSetter("teilnehmer")
    public void setTeilnehmer(ArrayList<Student> teilnehmer) {
        this.teilnehmer = teilnehmer;
    }

    @JsonGetter("abgaben")
    public ArrayList<Abgabe> getAbgaben() {
        return abgaben;
    }

    @JsonSetter("abgaben")
    public void setAbgaben(ArrayList<Abgabe> abgaben) {
        this.abgaben = abgaben;
    }

    @JsonGetter("reviews")
    public ArrayList<Review> getReviews() {
        return reviews;
    }

    @JsonSetter("reviews")
    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }
}
