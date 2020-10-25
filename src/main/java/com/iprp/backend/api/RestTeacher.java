package com.iprp.backend.api;

import com.iprp.backend.workshop.Workshop;
import org.springframework.web.bind.annotation.*;

/**
 * Rest Routes des Teachers
 * @author Georg Reisinger
 */
@RestController
public class RestTeacher {

    /**
     * Leherer Dashboard
     * @return JSON mit den Dashboard Daten
     */
    @GetMapping("/teacher")
    public String rest_get_teacher(){
        return "{inhalt: inhalt}";
    }

    /**
     * Workshop auswählen
     * @param id ID des Workshops
     * @return JSON mit den Daten des Workshops
     */
    @GetMapping("/teacher/workshop")
    public String rest_get_teacher_workshop(int id){
        return "{inhalt: inhalt}";
    }

    /**
     * Editiert einen Workshop
     * @param workshop Neues Objekt des Workshops
     */
    @PutMapping("/teacher/workshop")
    public void rest_put_teacher_workshop(Workshop workshop){
    }

    /**
     * Löscht einen Workshop
     * @param id Workshop id die zum löschen ist
     */
    @DeleteMapping("/teacher/workshop")
    public void rest_del_teacher_workshop(int id){
    }

    /**
     * Erstellt einen Workshop
     * @return id des erstellten workshops
     */
    @PostMapping("/teacher/workshop")
    public int rest_post_teacher_workshop(int id){
        return 0;
    }

    /**
     * Alle workshops des aktuellen Lehrers
     * @return JSON mit allen Workshops des Lehrers
     */
    @GetMapping("/teacher/workshops")
    public String rest_get_teacher_workshops(){
        return "{inhalt: inhalt}";
    }

    /**
     * Zeigt alle zuweisungen eines gewählten workshops
     * @param id Ausgewählter Workshop
     * @return JSON mit den zuteilungen
     */
    @GetMapping("/teacher/workshop/assign")
    public String rest_get_teacher_workshop_assign(int id){
        return "{inhalt: inhalt}";
    }

    /**
     * Manuelle zuteilung
     * @param id Workshop zu dem zugeteilt wird
     * @param student1ID Erste Studenetenid
     * @param student2ID Zweite Studenetenid
     */
    @PutMapping("/teacher/workshop/assign")
    public void rest_put_teacher_workshop_assign(int id, int student1ID, int student2ID){
    }

    /**
     * Automatische zuteilung starten
     * @param id Workshop für die Zuteilung
     */
    @PutMapping("/teacher/workshop/assign/auto")
    public void rest_put_teacher_workshop_assign_auto(int id){
    }

    /**
     * Alle Reviews eines Workshops
     * @param id Workshop id
     * @return JSON mit den Reviews
     */
    @GetMapping("/teacher/reviews")
    public String rest_get_teacher_reviews(int id){
        return "{inhalt: inhalt}";
    }

    /**
     * Einzelnes Review ausgeben
     * @param reviewID Review ID
     * @return JSON mit dem Review
     */
    @GetMapping("/teacher/review")
    public String rest_get_teacher_review(int reviewID){
        return "{inhalt: inhalt}";
    }

    /**
     * Alle abgaben von einem Workshop
     * @param id Workshop ID
     * @return JSON mit den abgaben
     */
    @GetMapping("/teacher/deliverys")
    public String rest_get_teacher_deliverys(int id){
        return "{inhalt: inhalt}";
    }

    /**
     * Einzelen Abgabe ausgeben
     * @param deliveryid Abgaben ID
     * @return JSON mit der Abgabe
     */
    @GetMapping("/teacher/delivery")
    public String rest_get_teacher_delivery(int deliveryid){
        return "{inhalt: inhalt}";
    }
}
