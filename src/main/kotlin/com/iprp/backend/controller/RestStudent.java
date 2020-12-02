package com.iprp.backend.controller;


import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

//@RestController
public class RestStudent {

    /**
     * Studierende Dashboard
     * @return JSON mit den Dashboard Daten
     */
    @GetMapping("/student")
    public String rest_get_student(){
        return "{inhalt: inhalt}";
    }

    /**
     * Workshop auswählen
     * @param id ID des Workshops
     * @return JSON mit den Daten des Workshops
     */
    @GetMapping("/student/workshop")
    public String rest_get_student_workshop(int id){
        return "{inhalt: inhalt}";
    }

    /**
     * Alle workshops des aktuellen Studeneten
     * @return JSON mit allen Workshops des Lehrers
     */
    @GetMapping("/student/workshops")
    public String rest_get_student_workshops(){
        return "{inhalt: inhalt}";
    }

    /**
     * Alle Reviews des Studenten
     * @param id Studenten id
     * @return JSON mit den Reviews
     */
    @GetMapping("/student/reviews")
    public String rest_get_student_reviews(int id){
        return "{inhalt: inhalt}";
    }

    /**
     * Einzelnes Review ausgeben
     * @param reviewID Review ID
     * @return JSON mit dem Review
     */
    @GetMapping("/student/review")
    public String rest_get_student_review(int reviewID){
        return "{inhalt: inhalt}";
    }

    /**
     * Bearbeitet Einzelnes Review ausgeben
     */
    @PutMapping("/student/review")
    public void rest_put_student_review(String json){
    }

    /**
     * Erstellt Review
     */
    @PostMapping("/student/review")
    public void rest_post_student_review(String json){
    }
    /**
     * Löscht Einzelnes Review ausgeben
     * @param reviewID Review ID
     */
    @DeleteMapping("/student/review")
    public void rest_del_student_review(int reviewID){
    }
    /**
     * Download Einzelnes Review
     * @param reviewID Review ID
     */
    @GetMapping("/student/review/dl")
    public void rest_get_student_review_dl(int reviewID){
    }


    /**
     * Bearbeitet eine Abgabe
     */
    @PutMapping("/student/delivery")
    public void rest_put_student_delivery(String json){
    }

    /**
     * Einzelen Abgabe ausgeben
     * @param deliveryid Abgaben ID
     * @return JSON mit der Abgabe
     */
    @GetMapping("/student/delivery")
    public String rest_get_student_delivery(int deliveryid){
        return "{inhalt: inhalt}";
    }

    /**
     * Erstellt abgabe
     */
    @PostMapping("/student/delivery")
    public void rest_get_student_delivery(String json){
    }
}
