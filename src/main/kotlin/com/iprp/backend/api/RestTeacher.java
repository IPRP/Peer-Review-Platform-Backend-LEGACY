package com.iprp.backend.api;

import com.iprp.backend.workshop.Workshop;
import org.springframework.web.bind.annotation.*;

/**
 * Rest Routes des Teachers
 * @author Georg Reisinger
 */
//@RestController
public class RestTeacher {

    /**
     * Leherer Dashboard
     * @return JSON mit den Dashboard Daten
     */
    @GetMapping("/teacher")
    public String teacher(){
        return "{inhalt: inhalt}";
    }

    /**
     * Workshop auswählen
     * @return JSON mit den Daten des Workshops
     */
    @GetMapping("/teacher/workshop")
    public String getteacherworkshop(@RequestBody String payload){
        return "{inhalt: inhalt}";
    }

    /**
     * Editiert einen Workshop
     */
    @PutMapping("/teacher/workshop")
    public void putteacherworkshop(@RequestBody String payload){
    }

    /**
     * Löscht einen Workshop
     */
    @DeleteMapping("/teacher/workshop")
    public void delteacherworkshop(@RequestBody String payload){
    }

    /**
     * Erstellt einen Workshop
     * @return id des erstellten workshops
     */
    @PostMapping("/teacher/workshop")
    public int postteacherworkshop(@RequestBody String payload){
        return 0;
    }

    /**
     * Alle workshops des aktuellen Lehrers
     * @return JSON mit allen Workshops des Lehrers
     */
    @GetMapping("/teacher/workshops")
    public String teacherworkshops(){
        return "{inhalt: inhalt}";
    }

    /**
     * Zeigt alle zuweisungen eines gewählten workshops
     * @return JSON mit den zuteilungen
     */
    @GetMapping("/teacher/workshop/assign")
    public String getteacherworkshopassign(@RequestBody String payload){
        return "{inhalt: inhalt}";
    }

    /**
     * Manuelle zuteilung
     */
    @PutMapping("/teacher/workshop/assign")
    public void putteacherworkshopassign(@RequestBody String payload){
    }

    /**
     * Automatische zuteilung starten
     */
    @PutMapping("/teacher/workshop/assign/auto")
    public void putteacherworkshopassignauto(@RequestBody String payload){
    }

    /**
     * Alle Reviews eines Workshops
     * @return JSON mit den Reviews
     */
    @GetMapping("/teacher/reviews")
    public String getteacherreviews(@RequestBody String payload){
        return "{inhalt: inhalt}";
    }

    /**
     * Einzelnes Review ausgeben
     * @return JSON mit dem Review
     */
    @GetMapping("/teacher/review")
    public String getteacherreview(@RequestBody String payload){
        return "{inhalt: inhalt}";
    }

    /**
     * Alle abgaben von einem Workshop
     * @return JSON mit den abgaben
     */
    @GetMapping("/teacher/deliverys")
    public String getteacherdeliverys(@RequestBody String payload){
        return "{inhalt: inhalt}";
    }

    /**
     * Einzelen Abgabe ausgeben
     * @return JSON mit der Abgabe
     */
    @GetMapping("/teacher/delivery")
    public String getteacherdelivery(@RequestBody String payload){
        return "{inhalt: inhalt}";
    }
}
