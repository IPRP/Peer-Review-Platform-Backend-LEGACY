package com.iprp.backend.api;


import com.iprp.backend.delivery.Delivery;
import com.iprp.backend.enums.STATUS;
import com.iprp.backend.review.Review;
import org.springframework.web.bind.annotation.*;

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
     * @param statusFilter Filter für den Status der Reviews
     * @return JSON mit den Reviews
     */
    @GetMapping("/student/reviews")
    public String rest_get_student_reviews(int id, STATUS statusFilter){
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
     * @param review neues review Obj
     */
    @PutMapping("/student/review")
    public void rest_put_student_review(Review review){
    }

    /**
     * Erstellt Review
     * @param review neues review Obj
     */
    @PostMapping("/student/review")
    public void rest_post_student_review(Review review){
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
     * @param delivery neues Abgabe Obj
     */
    @PutMapping("/student/delivery")
    public void rest_put_student_delivery(Delivery delivery){
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
     * @param delivery neues Abgabe Obj
     */
    @PostMapping("/student/delivery")
    public void rest_get_student_delivery(Delivery delivery){
    }
}
