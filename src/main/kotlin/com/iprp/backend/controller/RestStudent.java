package com.iprp.backend.controller;


import com.iprp.backend.controller.helper.JsonHelper;
import com.iprp.backend.repos.WrapperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

@RestController
public class RestStudent {

    @Autowired
    private com.iprp.backend.DataManagement datamanagement;

    @Autowired
    private WrapperRepository repo;

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/student/workshop")
    public Map<String, Object> rest_get_student_workshop(@RequestBody String json) {
        Map<String, Object> map = JsonHelper.jsonPayloadToMap(json);
        return datamanagement.getStudentWorkshop(map.get("studentId").toString(), map.get("workshopId").toString());
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/student/workshops")
    public Map<String, List<Map<String, String>>> rest_get_student_workshops(@RequestBody String json) {
        Map<String, Object> map = JsonHelper.jsonPayloadToMap(json);
        assert map != null;
        return datamanagement.getAllWorkshops(map.get("personId").toString());
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/student/todos")
    public Map<String, Object> rest_get_student_delivery(@RequestBody String json) {
        Map<String, Object> map = JsonHelper.jsonPayloadToMap(json);
        assert map != null;
        return datamanagement.getStudentTodos(map.get("studentId").toString());
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping("/submission")
    public Map<String, Object> rest_addsumbmission(@RequestBody String json) {
        Map<String, Object> map = JsonHelper.jsonPayloadToMap(json);
        assert map != null;
        return datamanagement.addSubmissionToWorkshop(map.get("studentId").toString(), map.get("workshopId").toString(), map.get("title").toString(), map.get("comment").toString(), (List<? extends Map<String, String>>) map.get("attatchments"));
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/submission")
    public Map<String, Object> rest_submissionsget(@RequestBody String json) {
        Map<String, Object> map = JsonHelper.jsonPayloadToMap(json);
        assert map != null;
        return datamanagement.getSubmissionStudent(map.get("studentId").toString(), map.get("submissionId").toString());
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @PutMapping("/submission")
    public Map<String, Object> putsubmission(@RequestBody String json) {
        Map<String, Object> map = JsonHelper.jsonPayloadToMap(json);
        assert map != null;
        return datamanagement.updateSubmission(map.get("studentId").toString(), map.get("submissionId").toString(), map.get("title").toString(), map.get("comment").toString(), (List<? extends Map<String, String>>) map.get("attatchments"));
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping("/submission/upload/")
    public Map<String, Object> addFile(@RequestParam("title") String title,
                                       @RequestParam("submissionId") String submissionId,
                                       @RequestParam("file") MultipartFile file) {
        return datamanagement.uploadAttachment(title, file);
    }


    @CrossOrigin(origins = "http://localhost:8081")
    @DeleteMapping("/submission/remove/")
    public Map<String, Object> delSub(@RequestBody String json) {
        Map<String, Object> map = JsonHelper.jsonPayloadToMap(json);
        assert map != null;
        return datamanagement.removeAttachment(map.get("attId").toString());
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/submission/download/")
    public InputStream getAtt(@RequestBody String json) {
        Map<String, Object> map = JsonHelper.jsonPayloadToMap(json);
        assert map != null;
        return datamanagement.downloadAttachment(map.get("attId").toString());
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @PutMapping("/review")
    public Map<String, Object> putReview(@RequestBody String json, @RequestParam("points") List<Integer> points) {
        Map<String, Object> map = JsonHelper.jsonPayloadToMap(json);
        assert map != null;
        return datamanagement.updateReview(map.get("studentId").toString(), map.get("id").toString(), map.get("feedback").toString(), points);
    }
}
