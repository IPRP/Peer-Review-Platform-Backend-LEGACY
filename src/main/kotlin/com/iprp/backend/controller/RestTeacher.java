package com.iprp.backend.controller;

import com.iprp.backend.controller.helper.JsonHelper;
import com.iprp.backend.controller.obj.*;
import com.iprp.backend.repos.WrapperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;


@RestController
public class RestTeacher {

    /*Neue Testdaten für newTeacher */
    private ArrayList<String> members = new ArrayList<>();
    private ArrayList<DoneOpenSubmissions> submissions = new ArrayList<DoneOpenSubmissions>();
    private ArrayList<DoneOpenReviews> reviews = new ArrayList<DoneOpenReviews>();
    private ArrayList<SubmissionID> subDone = new ArrayList<SubmissionID>();
    private ArrayList<SubmissionID> subOpen = new ArrayList<SubmissionID>();
    private ArrayList<ReviewID> revDone = new ArrayList<ReviewID>();
    private ArrayList<ReviewID> revOpen = new ArrayList<ReviewID>();
    private ArrayList<Kriterium> kriterien = new ArrayList<Kriterium>();
    private Workshop workshop;
    private ArrayList<Workshop> workshops = new ArrayList<Workshop>();

    @Autowired
    private com.iprp.backend.DataManagement sdfgfg;

    @Autowired
    private WrapperRepository repo;

    private void initNewTestData(){
        this.members = new ArrayList<>();
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
//        this.subOpen.add(new SubmissionID(1, "Georg open", true));
//        this.revOpen.add(new ReviewID(2, "Lukas open", "Georg"));
//        this.subDone.add(new SubmissionID(3, "Georg done", true));
//        this.revDone.add(new ReviewID(4, "Lukas done", "Georg"));
        this.submissions.add(new DoneOpenSubmissions(subDone, subOpen));
        this.reviews.add(new DoneOpenReviews(revDone, revOpen));
        this.workshop = new Workshop("1","Workshop Name 1", "Beschreibung 1", "11-11-2020 11:11", false, this.members, this.submissions, this.reviews, this.kriterien);
        this.workshops.add(this.workshop);
        this.workshops.add(new Workshop("2","Workshop Name 2", "Beschreibung 2", "12-12-2020 12:12", false, this.members, this.submissions, this.reviews, this.kriterien));
    }

    /**
     * Editiert einen Workshop
     *
     */
    @CrossOrigin(origins = "http://localhost:8081/")
    @PutMapping("/teacher/workshop")
    public String putteacherworkshop(@RequestBody String payload){
        Workshop listWorkshop;
        listWorkshop = new JsonHelper(payload).generateWorkshop();
        System.out.println(listWorkshop.getTitle());
        return sdfgfg.updateWorkshopNEU(listWorkshop.getId(), listWorkshop.getMembers(), listWorkshop.getTitle(), listWorkshop.getBeschreibung(), LocalDateTime.now().plusDays(1)).toString();
    }


    /**
     * Erstellt einen Workshop
     * @return id des erstellten workshops
     *
     */
    @CrossOrigin(origins = "http://localhost:8081/")
    @PostMapping(value="/teacher/workshop", produces = MediaType.APPLICATION_JSON_VALUE)
    public String postteacherworkshop(@RequestBody String json){
        sdfgfg.addTeacher("teacher", "teacher", "teacher");
        sdfgfg.addStudent("Georg", "Georg", "Georg", "");
        sdfgfg.addStudent("Lukas", "Lukas", "Lukas", "");
        workshop = new JsonHelper(json).generateWorkshop();
        ArrayList<String> te = new ArrayList<>();
        te.add("teacher");
        ArrayList<Map<String, String>> ar = new ArrayList<>();
        Map<String, String> map = new java.util.HashMap<>(Collections.emptyMap());
        map.put("type", "point");
        map.put("title", workshop.getTitle());
        map.put("content", workshop.getBeschreibung());
        ar.add(map);
        Map<String, Object> map2 =sdfgfg.addWorkshop(te, workshop.getMembers(), workshop.getTitle(), workshop.getBeschreibung(), false, LocalDateTime.now().plusDays(1), ar);
        for (DoneOpenSubmissions dor : workshop.getSubmissions()){
            for (SubmissionID opensu : dor.getOpen()){
                sdfgfg.addSubmissionToWorkshop(opensu.getName(), workshop.getId(), "", "", new ArrayList<Map<String, String>>());
            }
            for (SubmissionID donesu : dor.getDone()){
                sdfgfg.addSubmissionToWorkshop(donesu.getName(), workshop.getId(), "", "", new ArrayList<Map<String, String>>());
            }
        }
        System.out.println(map2.toString());
        return map2.toString();
    }


    @CrossOrigin(origins = "http://localhost:8081")
    @DeleteMapping(value="/teacher/workshop/{id}")
    public String techerdelworkshop(@PathVariable String id){
        System.out.println("DEL workshops");
        System.out.println(id);
        sdfgfg.deleteWorkshop(id);
        return getWorkshops();
    }


    private String getWorkshops(){
        System.out.println("GET workshops");
        List<com.iprp.backend.data.Workshop> workshops = repo.findAllWorkshops("teacher");
        ArrayList<Workshop> myWorkshops = new ArrayList<>();
        ArrayList<String> students = new ArrayList<>();
        ArrayList<com.iprp.backend.data.submission.Submission> submissions = new ArrayList<>();
        ArrayList<com.iprp.backend.data.review.Review> reviews = new ArrayList<>();
        ArrayList<String> stunames = new ArrayList<>();
        for (com.iprp.backend.data.Workshop wo : workshops){
            for (String stu : wo.getStudents()){
                students.add(repo.findStudent(stu).getId());
                submissions.addAll(repo.findAllStudentSubmissionsInWorkshop(stu, wo.getId()));
                reviews.addAll(repo.findAllStudentReviewsInWorkshop(stu, wo.getId()));
                stunames.add(stu);
            }
            ArrayList<SubmissionID> mySubmissionsDone = new ArrayList<>();
            ArrayList<SubmissionID> mySubmissionsOpen = new ArrayList<>();
            for(com.iprp.backend.data.submission.Submission su : submissions){
                if(su.getReviewsDone()){
                    mySubmissionsDone.add(new SubmissionID(su.getId(), su.getTitle(), false));
                }else{
                    mySubmissionsOpen.add(new SubmissionID(su.getId(), su.getTitle(), false));
                }
            }
            ArrayList<ReviewID> myReviewDone = new ArrayList<>();
            ArrayList<ReviewID> myReviewOpen = new ArrayList<>();
            int count = 0;
            for(com.iprp.backend.data.review.Review re : reviews){
                if(re.getDone()){
                    myReviewDone.add(new ReviewID(re.getId(), re.getStudent(), stunames.get(count)));
                }else {
                    myReviewOpen.add(new ReviewID(re.getId(), re.getStudent(), stunames.get(count)));
                }
                count++;
            }
            ArrayList<DoneOpenSubmissions> myDoneOpenSubmissions = new ArrayList<>();
            ArrayList<DoneOpenReviews> myDoneOpenReviews = new ArrayList<>();
            myDoneOpenReviews.add(new DoneOpenReviews(myReviewDone, myReviewOpen));
            myDoneOpenSubmissions.add(new DoneOpenSubmissions(mySubmissionsDone, mySubmissionsOpen));
            myWorkshops.add(new Workshop(wo.getId(), wo.getTitle(), wo.getContent(), wo.getEnd().toString(), wo.getAnonymous(), students, myDoneOpenSubmissions, myDoneOpenReviews, null));
        }

        return new JsonHelper(myWorkshops).generateJson();
    }

    /**
     * Alle workshops des aktuellen Lehrers
     * @return JSON mit allen Workshops des Lehrers
     */
    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping(value="/teacher/workshops", produces = MediaType.APPLICATION_JSON_VALUE)
    public String teacherworkshops(){
       return getWorkshops();
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