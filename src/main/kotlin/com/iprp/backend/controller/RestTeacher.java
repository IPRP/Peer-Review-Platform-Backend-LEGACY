package com.iprp.backend.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iprp.backend.helper.JsonHelper;
import com.iprp.backend.user.*;
import com.iprp.backend.workshop.Workshop;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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


    /*Neue Testdaten für newTeacher */
    private ArrayList<String> members = new ArrayList<String>();
    private ArrayList<DoneOpenSubmissions> submissions = new ArrayList<DoneOpenSubmissions>();
    private ArrayList<DoneOpenReviews> reviews = new ArrayList<DoneOpenReviews>();
    private ArrayList<SubmissionID> subDone = new ArrayList<SubmissionID>();
    private ArrayList<SubmissionID> subOpen = new ArrayList<SubmissionID>();
    private ArrayList<ReviewID> revDone = new ArrayList<ReviewID>();
    private ArrayList<ReviewID> revOpen = new ArrayList<ReviewID>();
    private NewWorkshop newWorkshop;
    private ArrayList<NewWorkshop> workshops = new ArrayList<NewWorkshop>();;
    private void initNewTestData(){
        this.members = new ArrayList<String>();
        this.submissions = new ArrayList<DoneOpenSubmissions>();
        this.reviews = new ArrayList<DoneOpenReviews>();
        this.subOpen = new ArrayList<SubmissionID>();
        this.revOpen = new ArrayList<ReviewID>();
        this.revDone = new ArrayList<ReviewID>();
        this.subDone = new ArrayList<SubmissionID>();
        this.workshops = new ArrayList<NewWorkshop>();;
        this.members.add("Lukas");
        this.members.add("Georg");
        this.subOpen.add(new SubmissionID(1, "Georg open", true));
        this.revOpen.add(new ReviewID(2, "Lukas open", "Georg"));
        this.subDone.add(new SubmissionID(3, "Georg done", true));
        this.revDone.add(new ReviewID(4, "Lukas done", "Georg"));
        this.submissions.add(new DoneOpenSubmissions(subDone, subOpen));
        this.reviews.add(new DoneOpenReviews(revDone, revOpen));
        this.newWorkshop = new NewWorkshop(5, "Workshop name", this.members, this.submissions, this.reviews);
        this.workshops.add(this.newWorkshop);
        this.workshops.add(new NewWorkshop(6, "Workshop name2", this.members, this.submissions, this.reviews));
    }
    /* Neue Testdaten END */

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
    public String putteacherworkshop(@RequestBody String payload){
        initTestdata();
        List<Workshop> listWorkshop;
        try {
            listWorkshop = new JsonHelper(payload).generateWorkshopList();
            System.out.println(listWorkshop.toString());
            this.teacher.editWorkshop(listWorkshop.get(0), listWorkshop.get(1));
            return new JsonHelper("erfolgreich").generateJson();
        } catch (JsonProcessingException e) {
            System.out.println("Error " + e.getMessage());
            return new JsonHelper("JSON error -> " + e.getMessage()).generateJson();
        }

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
    public String postteacherworkshop(@RequestBody String payload){
        initTestdata();
        return "";
    }

    /**
     * Alle workshops des aktuellen Lehrers
     * @return JSON mit allen Workshops des Lehrers
     */
    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping(value="/teacher/workshops", produces = MediaType.APPLICATION_JSON_VALUE)
    public String teacherworkshops(){
        //initTestdata();
        initNewTestData();
        System.out.println("GET workshops");
        return new JsonHelper(this.workshops).generateJson();
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

