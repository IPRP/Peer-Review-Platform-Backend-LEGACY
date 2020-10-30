package com.iprp.backend.api;


import com.iprp.backend.delivery.Delivery;
import com.iprp.backend.enums.STATUS;
import com.iprp.backend.review.Review;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestStudent {

    /**
     * Studierende Dashboard
     * @return JSON mit den Dashboard Daten
     */
    @GetMapping("/student")
    public String restgetstudent(){
        return "{inhalt: inhalt}";
    }

    /**
     * Workshop auswählen
     * @param id ID des Workshops
     * @return JSON mit den Daten des Workshops
     */
    @GetMapping("/student/workshop")
    public String restgetstudentworkshop(int id){
        return "{inhalt: inhalt}";
    }

    /**
     * Alle workshops des aktuellen Studeneten
     * @return JSON mit allen Workshops des Lehrers
     */
    @GetMapping("/student/workshops")
    public String restgetstudentworkshops(){
        return "{inhalt: inhalt}";
    }

    /**
     * Alle Reviews des Studenten
     * @param id Studenten id
     * @param statusFilter Filter für den Status der Reviews
     * @return JSON mit den Reviews
     */
    @GetMapping("/student/reviews")
    public String restgetstudentreviews(int id, STATUS statusFilter){
        return "{inhalt: inhalt}";
    }

    /**
     * Einzelnes Review ausgeben
     * @param reviewID Review ID
     * @return JSON mit dem Review
     */
    @GetMapping("/student/review")
    public String restgetstudentreview(int reviewID){
        return "{inhalt: inhalt}";
    }

    /**
     * Bearbeitet Einzelnes Review ausgeben
     * @param review neues review Obj
     */
    @PutMapping("/student/review")
    public void restputstudentreview(Review review){
    }

    /**
     * Erstellt Review
     * @param review neues review Obj
     */
    @PostMapping("/student/review")
    public void restpoststudentreview(Review review){
    }
    /**
     * Löscht Einzelnes Review ausgeben
     * @param reviewID Review ID
     */
    @DeleteMapping("/student/review")
    public void restdelstudentreview(int reviewID){
    }
    /**
     * Download Einzelnes Review
     * @param reviewID Review ID
     */
    @GetMapping("/student/review/dl")
    public void restgetstudentreviewdl(int reviewID){
    }


    /**
     * Bearbeitet eine Abgabe
     * @param delivery neues Abgabe Obj
     */
    @PutMapping("/student/delivery")
    public void restputstudentdelivery(Delivery delivery){
    }

    /**
     * Einzelen Abgabe ausgeben
     * @param deliveryid Abgaben ID
     * @return JSON mit der Abgabe
     */
    @GetMapping("/student/delivery")
    public String restgetstudentdelivery(int deliveryid){
        return "{inhalt: inhalt}";
    }

    /**
     * Erstellt abgabe
     * @param delivery neues Abgabe Obj
     */
    @PostMapping("/student/delivery")
    public void restgetstudentdelivery(Delivery delivery){
    }
}
