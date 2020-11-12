package at.peer.platform.back.controller;

import at.peer.platform.back.helper.JsonHelper;
import at.peer.platform.back.user.Student;
import at.peer.platform.back.user.Teacher;
import at.peer.platform.back.workshop.Workshop;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class RestTeacher {

    /* Testdaten */
    private Teacher teacher = new Teacher("1", "Max", "Muster");
    private Student student = new Student("2", "Schuller", "Schuh");
    private Workshop workshop1 = new Workshop("Workshop1");
    private Workshop workshop2 = new Workshop("Workshop2");

    private void initTestdata(){
        this.teacher = new Teacher("1", "Max", "Muster");
        this.student = new Student("2", "Schuller", "Schuh");
        this.workshop1 = new Workshop("Workshop1");
        this.workshop2 = new Workshop("Workshop2");
        this.workshop1.addTeilnehmer(student);
        this.workshop2.addTeilnehmer(student);
        this.teacher.addWorkshop(this.workshop1);
        this.teacher.addWorkshop(this.workshop2);

    }
    /* Testdaten END */

    /**
     * Leherer Dashboard
     * @return JSON mit den Dashboard Daten
     */
    @GetMapping("/teacher")
    public String teacher(){
        initTestdata();
        return new JsonHelper(this.teacher).generateJson();
    }

    /**
     * Workshop auswählen
     * @param payload Json Payload -> Workshop id
     * @return JSON mit den Daten des Workshops
     */
    @GetMapping("/teacher/workshop")
    public String getteacherworkshop(@RequestBody String payload){
        initTestdata();
        return new JsonHelper(this.teacher.getWorkshop(payload)).generateJson();
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
        initTestdata();
        return new JsonHelper(this.teacher.getWorkshops()).generateJson();
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

