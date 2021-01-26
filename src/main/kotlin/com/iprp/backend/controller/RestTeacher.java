package com.iprp.backend.controller;

import com.iprp.backend.controller.helper.JsonHelper;
import com.iprp.backend.controller.obj.*;
import com.iprp.backend.data.review.Review;
import com.iprp.backend.data.review.ReviewCriteria;
import com.iprp.backend.data.review.ReviewCriterionType;
import com.iprp.backend.data.submission.Submission;
import com.iprp.backend.data.user.Student;
import com.iprp.backend.repos.WrapperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;


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
    ArrayList<com.iprp.backend.data.user.Student> foundStudent = new ArrayList<>();
    @Autowired
    private com.iprp.backend.DataManagement datamanagement;

    @Autowired
    private WrapperRepository repo;

//    private void initNewTestData(){
//        this.members = new ArrayList<>();
//        this.submissions = new ArrayList<DoneOpenSubmissions>();
//        this.reviews = new ArrayList<DoneOpenReviews>();
//        this.subOpen = new ArrayList<SubmissionID>();
//        this.revOpen = new ArrayList<ReviewID>();
//        this.revDone = new ArrayList<ReviewID>();
//        this.subDone = new ArrayList<SubmissionID>();
//        this.workshops = new ArrayList<Workshop>();
//        this.kriterien = new ArrayList<Kriterium>();
//        this.kriterien.add(new Kriterium(1, "Kriterium 1 Ja Nein", "Beschreibung 1", true, -1, -1));
//        this.kriterien.add(new Kriterium(2, "Kriterium 2 Punkte", "Beschreibung 2", false, -1, 100));
//        this.kriterien.add(new Kriterium(3, "Kriterium 3 Prozent", "Beschreibung 3", false, -1, 100));
//        this.members.add("Lukas");
//        this.members.add("Georg");
//        this.submissions.add(new DoneOpenSubmissions(subDone, subOpen));
//        this.reviews.add(new DoneOpenReviews(revDone, revOpen));
//        this.workshop = new Workshop("1","Workshop Name 1", "Beschreibung 1", "11-11-2020 11:11", false, this.members, this.submissions, this.reviews, this.kriterien);
//        this.workshops.add(this.workshop);
//        this.workshops.add(new Workshop("2","Workshop Name 2", "Beschreibung 2", "12-12-2020 12:12", false, this.members, this.submissions, this.reviews, this.kriterien));
//    }

    @CrossOrigin(origins = "http://localhost:8081/")
    @DeleteMapping("/del/all")
    public void delALL(){
        repo.deleteAll();
    }


    /**
     * Editiert einen Workshop
     *
     * @return
     */
    @CrossOrigin(origins = "*")
    @PutMapping("/teacher/workshop")
    public Map<String, Object> putteacherworkshop(@RequestBody String payload, Authentication authentication){
        if (authentication == null) {
            return Collections.singletonMap("ok", false);
        }
        Workshop listWorkshop;
        listWorkshop = new JsonHelper(payload).generateWorkshop();
        System.out.println(new JsonHelper(listWorkshop).generateJson());
        Map<String, Object> workshop = datamanagement.getTeacherWorkshop(listWorkshop.getId());
        System.out.println("workshop ausgabe");
        System.out.println(workshop);
        com.iprp.backend.data.Workshop workshop1 = new com.iprp.backend.data.Workshop(listWorkshop.getId(), listWorkshop.getBeschreibung(), LocalDateTime.now().plusDays(1), listWorkshop.isAnonym(), getStudent(listWorkshop), new ArrayList<>(), "");
        return datamanagement.updateWorkshop(listWorkshop.getId(), workshop1.getTeachers(), getStudent(listWorkshop), listWorkshop.getTitle(), listWorkshop.getBeschreibung(), workshop1.getEnd(), new ArrayList<>());
    }

    /**
     * Erstellt einen Workshop
     * @return id des erstellten workshops
     *
     */
    @CrossOrigin(origins = "*")
    @PostMapping(value="/teacher/workshop", produces = MediaType.APPLICATION_JSON_VALUE)
    public String postteacherworkshop(@RequestBody String json, Authentication authentication){
        if (authentication == null) {
            return Collections.singletonMap("ok", false).toString();
        }
        //Erstellt user
        datamanagement.addTeacher("teacher", "teacher", "teacher");
        datamanagement.addStudent("Georg", "Georg", "Reisinger", " ");
        datamanagement.addStudent("Lukas", "Lukas", "Nowy", " ");
        //Erstellt Frontend Workshop
        workshop = new JsonHelper(json).generateWorkshop();
        //Teacher Backend
        ArrayList<String> te = new ArrayList<>();
        te.add("teacher");
        ArrayList<Map<String, String>> ar = new ArrayList<>();
        //Geht durch alle kriterien des frontends
        if(workshop != null && workshop.getKriterien() != null) {
            for (Kriterium kr : workshop.getKriterien()) {
                Map<String, String> map = new java.util.HashMap<>(Collections.emptyMap());
                System.out.println(kr.getType() + " " + kr.getName());
                map.put("type", kr.getType());
                map.put("title", kr.getName());
                map.put("content", kr.getBeschreibung());
                switch (kr.getType()) {
                    case "point":
                        break;
                    case "grade":
                        break;
                    case "percentage":
                        break;
                    case "truefalse":
                        break;
                }
                ar.add(map);
            }

            System.out.println(new JsonHelper(workshop).generateJson());
            datamanagement.addStudent("ge", "georg", "reisinger", "3D");
            datamanagement.addStudent("lu", "lukas", "nowy", "3D");
//            System.out.println("im rest conroller");
//            System.out.println(repo.findStudent("georg", "reisinger"));
//            this.foundStudent.add(repo.findStudent("georg", "reisinger"));
//            workshop.findStudent();
            Map<String, Object> map2 = datamanagement.addWorkshop(te, getStudent(workshop), workshop.getTitle(), workshop.getBeschreibung(), false, LocalDateTime.now().plusDays(1), ar);
            System.out.println(map2.toString());

            return map2.toString();
        }else{
            return "false";
        }
    }

    private ArrayList<String> getStudent(Workshop wo){
        this.foundStudent = new ArrayList<>();
        ArrayList<String> studentsid= new ArrayList<>();
        for (com.iprp.backend.controller.obj.Student stu:
                wo.getMembers()) {
            if(!stu.getFirstname().isEmpty() && !stu.getLastname().isEmpty()){
                Student tmpstu =  repo.findStudent("georg", "reisinger");
                System.out.println("tmpstu");
                System.out.println(tmpstu);
                System.out.println(tmpstu.getId());
                this.foundStudent.add(tmpstu);
                studentsid.add(tmpstu.getId());
            }

        }
        return studentsid;
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @DeleteMapping(value="/teacher/workshop/{id}")
    public String techerdelworkshop(@PathVariable String id, Authentication authentication){
        if (authentication == null) {
            return Collections.singletonMap("ok", false).toString();
        }
        System.out.println("DEL workshops");
        System.out.println(id);
        datamanagement.deleteWorkshop(id);
        return getWorkshops();
    }


    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping(value="/testget")
    public List<Map<String, Object>> testes(Authentication authentication){
       if (authentication == null) {
            return (List<Map<String, Object>>) Collections.singletonMap("ok", false);
        }
        ArrayList<Double> points = new ArrayList<>();
        points.add(10.0);
        datamanagement.updateReview("Georg", "5ff38e8c3836146dfe9dd7fc", "hhkl", points);
        Map<String, List<Map<String, String>>> wokshops = datamanagement.getAllWorkshops("teacher");
        List<Map<String, Object>> realworkshop = new LinkedList<>();
        for(Map<String, String> wo: wokshops.get("workshops")){
            realworkshop.add(datamanagement.getTeacherWorkshop(wo.get("id")));
        }
        return realworkshop;
    }

    private String getWorkshops(){
        List<com.iprp.backend.data.Workshop> workshops = repo.findAllWorkshops("teacher");
        ArrayList<Workshop> myWorkshops = new ArrayList<>();
        ArrayList<Student> students = new ArrayList<>();

        ArrayList<Submission> submissions = new ArrayList<>();
        System.out.println("GET workshops");
        //Geht durch alle workshops
        for (com.iprp.backend.data.Workshop wo : workshops) {
            System.out.println("Workshop");
//            Geht durch alle students
            for (String stu : wo.getStudents()) {
                System.out.println("stu: " + stu + " " + repo.findStudent(stu).getId());
                System.out.println(" WO id: " + wo.getId());
                students.add(repo.findStudent(stu));
                submissions.addAll(repo.findAllStudentSubmissionsInWorkshop(stu, wo.getId()));
            }
//            Geht durch alle submissions
            if (submissions != null) {
                System.out.println("Submission 0 title: " + submissions.toArray().toString());
                ArrayList<SubmissionID> mySubmissionsDone = new ArrayList<>();
                ArrayList<SubmissionID> mySubmissionsOpen = new ArrayList<>();
                ArrayList<Kriterium> kriteriumArrayList = new ArrayList<>();
                ArrayList<ReviewID> myReviewDone = new ArrayList<>();
                ArrayList<ReviewID> myReviewOpen = new ArrayList<>();
                for (Submission su : submissions) {
                    System.out.println("Submission title: " + su.getTitle());
                    if (su.getReviewsDone()) {
                        mySubmissionsDone.add(new SubmissionID(su.getId(), su.getTitle(), false));
                    } else {
                        mySubmissionsOpen.add(new SubmissionID(su.getId(), su.getTitle(), false));
                    }
                    //criteria
                    for (String reId : su.getReviews()) {
                        System.out.println("review id: " + reId);
                        Review review = repo.findReview(reId);
                        if (review != null) {
                            Student student = repo.findStudent(review.getStudent());
                            System.out.println("student name: " + student.getFirstname() + " " + student.getLastname());
                            if (review.getDone() && student != null) {
                                myReviewDone.add(new ReviewID(review.getId(), student.getFirstname() + " " + student.getLastname(),review.getFeedback()));
                            } else if (student != null) {
                                myReviewOpen.add(new ReviewID(review.getId(), student.getFirstname() + " " + student.getLastname(),review.getFeedback()));
                            }
                            ReviewCriteria criteria = repo.findReviewCriteria(review.getCriteria());
                            if (student != null && criteria != null) {
                                ArrayList<Map<String, Object>> points = new ArrayList<>();
                                for (BigDecimal i : review.getPoints()) {
                                    System.out.println("Points: " + i);
                                    System.out.println("Criteria: " + criteria.getCriteria().get(i.intValue()).getTitle() + criteria.getCriteria().get(i.intValue()).getContent());
                                    com.iprp.backend.data.review.ReviewCriterionType crty = criteria.getCriteria().get(i.intValue()).getType();
                                    if (crty.equals(ReviewCriterionType.Percentage)) {
                                        kriteriumArrayList.add(new Kriterium(0, criteria.getCriteria().get(i.intValue()).getTitle(), criteria.getCriteria().get(i.intValue()).getContent(), false, -1, review.getPoints().get(i.intValue()).doubleValue()));
                                    } else if (crty.equals(ReviewCriterionType.Point)) {
                                        kriteriumArrayList.add(new Kriterium(0, criteria.getCriteria().get(i.intValue()).getTitle(), criteria.getCriteria().get(i.intValue()).getContent(), false, review.getPoints().get(i.intValue()).doubleValue(), -1));
                                    } else if (crty.equals(ReviewCriterionType.Grade)) {
                                        // TODO
                                    } else if (crty.equals(ReviewCriterionType.TrueFalse)) {
                                        kriteriumArrayList.add(new Kriterium(0, criteria.getCriteria().get(i.intValue()).getTitle(), criteria.getCriteria().get(i.intValue()).getContent(), review.getPoints().get(i.intValue()).compareTo(new BigDecimal(0)) != 0, -1, -1));
                                    }
                                }
                            }
                        }
                    }
                }
//            Geht durch alle reviews
                ArrayList<DoneOpenSubmissions> myDoneOpenSubmissions = new ArrayList<>();
                ArrayList<DoneOpenReviews> myDoneOpenReviews = new ArrayList<>();
                myDoneOpenReviews.add(new DoneOpenReviews(myReviewDone, myReviewOpen));
                myDoneOpenSubmissions.add(new DoneOpenSubmissions(mySubmissionsDone, mySubmissionsOpen));
                ArrayList<com.iprp.backend.controller.obj.Student> studentsJava = new ArrayList<>();
                for (Student stu:
                     students) {
                    studentsJava.add(new com.iprp.backend.controller.obj.Student(stu.getId(), stu.getFirstname(), stu.getLastname(), stu.getGroup()));
                }

                myWorkshops.add(new Workshop(wo.getId(), wo.getTitle(), wo.getContent(), wo.getEnd().toString(), wo.getAnonymous(), studentsJava, myDoneOpenSubmissions, myDoneOpenReviews, kriteriumArrayList));
            }else{
                System.out.println("NO submission");
            }
        }
        return new JsonHelper(myWorkshops).generateJson();
    }

    /**
     * Alle workshops des aktuellen Lehrers
     * @return JSON mit allen Workshops des Lehrers
     */
    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping(value="/teacher/workshops", produces = MediaType.APPLICATION_JSON_VALUE)
    public String teacherworkshops(Authentication authentication){
        if (authentication == null) {
            return Collections.singletonMap("ok", false).toString();
        }
        return getWorkshops();
    }
}