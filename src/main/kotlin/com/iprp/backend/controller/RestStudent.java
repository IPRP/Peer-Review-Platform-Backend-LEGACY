package com.iprp.backend.controller;


import com.iprp.backend.controller.helper.JsonHelper;
import com.iprp.backend.repos.WrapperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
public class RestStudent {

    @Autowired
    private com.iprp.backend.DataManagement dm;

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping(value = "/student/workshop/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> rest_get_student_workshop(
            @PathVariable String id, Authentication authentication
    ) {
        if (id == null || authentication == null) {
            return Collections.singletonMap("ok", false);
        }
        return dm.getStudentWorkshop(authentication.getName(), id);
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping(value = "/student/workshops", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, List<Map<String, String>>> rest_get_student_workshops(Authentication authentication) {
        return dm.getAllWorkshops(authentication.getName());
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping(value = "/student/todos", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> rest_get_student_delivery(Authentication authentication){
        if (authentication == null) {
            return Collections.singletonMap("ok", false);
        }
        return dm.getStudentTodos(authentication.getName());
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping(value = "/submission/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> rest_addsumbmission(
            Authentication authentication, @PathVariable String id, @RequestBody Map<String, Object> json
    ){
        if (authentication == null ||
            !json.containsKey("title") || !json.containsKey("comment") || !json.containsKey("attachments")) {
            return Collections.singletonMap("ok", false);
        }

        return dm.addSubmissionToWorkshop(authentication.getName(), id,
                (String) json.get("title"), (String) json.get("comment"),
                (List<? extends Map<String, String>>) json.get("attachments")
        );

    }

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping(value = "/submission/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> rest_submissionsget(Authentication authentication, @PathVariable String id) {
        if (authentication == null || id == null) {
            return Collections.singletonMap("ok", false);
        }
        return dm.getSubmissionStudent(authentication.getName(), id);
    }


    @CrossOrigin(origins = "http://localhost:8081")
    @PutMapping("/submission/{id}")
    public Map<String, Object> putsubmission(
            Authentication authentication, @PathVariable String id, @RequestBody Map<String, Object> json
    ) {
        if (authentication == null || id == null ||
                !json.containsKey("title") || !json.containsKey("comment") || !json.containsKey("attachments")) {
            return Collections.singletonMap("ok", false);
        }

        return dm.updateSubmission(authentication.getName(), id,
                (String) json.get("title"), (String) json.get("comment"),
                (List<? extends Map<String, String>>) json.get("attachments")
        );
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @PostMapping("/submission/upload/")
    public Map<String, Object> addFile(@RequestParam("title") String title,
                          @RequestParam("submissionId") String submissionId,
                          @RequestParam("file") MultipartFile file){
        return dm.uploadAttachment(title, file);
    }


    @CrossOrigin(origins = "http://localhost:8081")
    @DeleteMapping("/submission/remove/")
    public Map<String, Object> delSub(@RequestBody String json){
        Map<String, Object> map = JsonHelper.jsonPayloadToMap(json);
        assert map != null;
        return dm.removeAttachment(map.get("attId").toString());
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/submission/download/")
    public InputStream getAtt(@RequestBody String json){
        Map<String, Object> map = JsonHelper.jsonPayloadToMap(json);
        assert map != null;
        return dm.downloadAttachment(map.get("attId").toString());
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @PutMapping("/review")
    public Map<String, Object> putReview(@RequestBody String json,  @RequestParam("points") List<Integer> points){
        Map<String, Object> map = JsonHelper.jsonPayloadToMap(json);
        assert map != null;
        return dm.updateReview(map.get("studentId").toString(),map.get("id").toString(),map.get("feedback").toString(), points);
    }
}
