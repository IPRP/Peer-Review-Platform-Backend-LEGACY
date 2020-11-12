package at.peer.platform.back.workshop;

import at.peer.platform.back.abgabe.Abgabe;
import at.peer.platform.back.review.Review;
import at.peer.platform.back.user.Student;
import at.peer.platform.back.user.Teacher;

import java.util.ArrayList;

public class Workshop {
    private String name, id;
    //private ArrayList<Teacher> lehrer;
    private ArrayList<Student> teilnehmer;
    private ArrayList<Abgabe> abgaben;
    private ArrayList<Review> reviews;

    public Workshop(String name) {
        this.name = name;
        this.id = name;
       /* this.lehrer = new ArrayList<Teacher>();
        this.lehrer.add(teacher);
        System.out.println("Techer ist da");*/
        this.teilnehmer = new ArrayList<Student>();
        this.abgaben = new ArrayList<Abgabe>();
        this.reviews = new ArrayList<Review>();
    }

    public String getId() {
        return id;
    }

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

    /*public void addLehrer(Teacher lehrer){
        this.lehrer.add(lehrer);
    }

    public void delLehrer(Teacher lehrer){
        this.lehrer.remove(lehrer);
    }

    public void editLehrer(Teacher lehrerOld, Teacher lehrerNeu){
        this.delLehrer(lehrerOld);
        this.addLehrer(lehrerNeu);
    }
*/
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

    public String getName() {
        return name;
    }

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


    public ArrayList<Student> getTeilnehmer() {
        return teilnehmer;
    }

    public void setTeilnehmer(ArrayList<Student> teilnehmer) {
        this.teilnehmer = teilnehmer;
    }

    public ArrayList<Abgabe> getAbgaben() {
        return abgaben;
    }

    public void setAbgaben(ArrayList<Abgabe> abgaben) {
        this.abgaben = abgaben;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }
}
