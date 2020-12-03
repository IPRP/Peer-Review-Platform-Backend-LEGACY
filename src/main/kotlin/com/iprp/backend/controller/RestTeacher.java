package com.iprp.backend.controller;

import com.iprp.backend.helper.JsonHelper;
import com.iprp.backend.user.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
public class RestTeacher {


    /*Neue Testdaten für newTeacher */
    private ArrayList<String> members = new ArrayList<String>();
    private ArrayList<DoneOpenSubmissions> submissions = new ArrayList<DoneOpenSubmissions>();
    private ArrayList<DoneOpenReviews> reviews = new ArrayList<DoneOpenReviews>();
    private ArrayList<SubmissionID> subDone = new ArrayList<SubmissionID>();
    private ArrayList<SubmissionID> subOpen = new ArrayList<SubmissionID>();
    private ArrayList<ReviewID> revDone = new ArrayList<ReviewID>();
    private ArrayList<ReviewID> revOpen = new ArrayList<ReviewID>();
    private ArrayList<Kriterium> kriterien = new ArrayList<Kriterium>();
    private Workshop workshop;
    private ArrayList<Workshop> workshops = new ArrayList<Workshop>();;
    private void initNewTestData(){
        this.members = new ArrayList<String>();
        this.submissions = new ArrayList<DoneOpenSubmissions>();
        this.reviews = new ArrayList<DoneOpenReviews>();
        this.subOpen = new ArrayList<SubmissionID>();
        this.revOpen = new ArrayList<ReviewID>();
        this.revDone = new ArrayList<ReviewID>();
        this.subDone = new ArrayList<SubmissionID>();
        this.workshops = new ArrayList<Workshop>();
        this.kriterien = new ArrayList<Kriterium>();
        this.kriterien.add(new Kriterium(1, "Kriterium 1 Ja Nein", "Beschreibung 1", 0, 0, true));
        this.kriterien.add(new Kriterium(2, "Kriterium 2 Punkte", "Beschreibung 2", 100, 0, false));
        this.kriterien.add(new Kriterium(3, "Kriterium 3 Prozent", "Beschreibung 3", 0, 100, false));
        this.members.add("Lukas");
        this.members.add("Georg");
        this.subOpen.add(new SubmissionID(1, "Georg open", true));
        this.revOpen.add(new ReviewID(2, "Lukas open", "Georg"));
        this.subDone.add(new SubmissionID(3, "Georg done", true));
        this.revDone.add(new ReviewID(4, "Lukas done", "Georg"));
        this.submissions.add(new DoneOpenSubmissions(subDone, subOpen));
        this.reviews.add(new DoneOpenReviews(revDone, revOpen));
        this.workshop = new Workshop(1,"Workshop Name 1", "Beschreibung 1", "11-11-2020 11:11", false, this.members, this.submissions, this.reviews, this.kriterien);
        this.workshops.add(this.workshop);
        this.workshops.add(new Workshop(2,"Workshop Name 2", "Beschreibung 2", "12-12-2020 12:12", false, this.members, this.submissions, this.reviews, this.kriterien));
    }
    /* Neue Testdaten END
    /**
     * Workshop auswählen
     * @param payload Json Payload -> Workshop id
     * @return JSON mit den Daten des Workshops
     *
    @GetMapping("/teacher/workshop")
    public String getteacherworkshop(@RequestBody String payload){
        initTestdata();
        return new JsonHelper(this.teacher.getWorkshop(payload)).generateJson();
    }

    /**
     * Editiert einen Workshop
     *
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
*/
    /**
     * Erstellt einen Workshop
     * @return id des erstellten workshops
     *
     */
    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping("/teacher/workshop")
    public String postteacherworkshop(@RequestBody String json){
        initNewTestData();
        Workshop workshop = null;
        System.out.println("POST");
        System.out.println(json);
        workshop = new JsonHelper(json).generateWorkshop();
        this.workshops.add(workshop);
         //return "true";
        return new JsonHelper(this.workshops).generateJson();
    }


    @CrossOrigin(origins = "http://localhost:8081")
    @DeleteMapping(value="/teacher/workshop/{id}")
    public String techerdelworkshop(@PathVariable String id){
        initNewTestData();
        System.out.println("DEL workshops");
        System.out.println(id);
        this.workshops.removeIf(work -> work.getId() == Integer.parseInt(id));
        return new JsonHelper(this.workshops).generateJson();
    }


    /**
     * Alle workshops des aktuellen Lehrers
     * @return JSON mit allen Workshops des Lehrers
     */
    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping(value="/teacher/workshops", produces = MediaType.APPLICATION_JSON_VALUE)
    public String teacherworkshops(){
        //initTestdata();
        //initNewTestData();
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