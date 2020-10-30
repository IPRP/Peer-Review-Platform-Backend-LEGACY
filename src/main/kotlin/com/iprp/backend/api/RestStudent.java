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
    public String student(){
        return "{inhalt: inhalt}";
    }

    /**
     * Workshop auswählen
     * @return JSON mit den Daten des Workshops
     */
    @GetMapping("/student/workshop")
    public String studentworkshop(@RequestBody String payload){
        return "{inhalt: inhalt}";
    }

    /**
     * Alle workshops des aktuellen Studeneten
     * @return JSON mit allen Workshops des Lehrers
     */
    @GetMapping("/student/workshops")
    public String studentworkshops(){
        return "{inhalt: inhalt}";
    }

    /**
     * Alle Reviews des Studenten
     * @return JSON mit den Reviews
     */
    @GetMapping("/student/reviews")
    public String studentreviews(@RequestBody String payload){
        return "{inhalt: inhalt}";
    }

    /**
     * Einzelnes Review ausgeben
     * @return JSON mit dem Review
     */
    @GetMapping("/student/review")
    public String getstudentreview(@RequestBody String payload){
        return "{inhalt: inhalt}";
    }

    /**
     * Bearbeitet Einzelnes Review ausgeben
     */
    @PutMapping("/student/review")
    public void putstudentreview(@RequestBody String payload){
    }

    /**
     * Erstellt Review
     */
    @PostMapping("/student/review")
    public void studentreview(@RequestBody String payload){
    }
    /**
     * Löscht Einzelnes Review ausgeben
     */
    @DeleteMapping("/student/review")
    public void restdelstudentreview(@RequestBody String payload){
    }
    /**
     * Download Einzelnes Review
     */
    @GetMapping("/student/review/dl")
    public void studentreviewdl(@RequestBody String payload){
    }


    /**
     * Bearbeitet eine Abgabe
     */
    @PutMapping("/student/delivery")
    public void putstudentdelivery(@RequestBody String payload){
    }

    /**
     * Einzelen Abgabe ausgeben
     * @return JSON mit der Abgabe
     */
    @GetMapping("/student/delivery")
    public String getstudentdelivery(@RequestBody String payload){
        return "{inhalt: inhalt}";
    }

    /**
     * Erstellt abgabe
     */
    @PostMapping("/student/delivery")
    public void poststudentdelivery(@RequestBody String payload){
    }
}
