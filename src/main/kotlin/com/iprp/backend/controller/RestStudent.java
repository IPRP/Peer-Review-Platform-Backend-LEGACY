package com.iprp.backend.controller;


import com.iprp.backend.attachments.AttachmentHandler;
import com.iprp.backend.controller.helper.JsonHelper;
import com.iprp.backend.repos.WrapperRepository;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
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
    @PutMapping(value = "/submission/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
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
    @PostMapping(value = "/submission/upload", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> addFile(
            @RequestParam("title") String title, @RequestParam("file") MultipartFile file) {
        return dm.uploadAttachment(title, file);
    }


    @CrossOrigin(origins = "http://localhost:8081")
    @DeleteMapping(value = "/submission/remove/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> delSub(@PathVariable String id){
        return dm.removeAttachment(id);
    }


    @CrossOrigin(origins = "http://localhost:8081")
    @GetMapping("/submission/download/{id}")
    public ResponseEntity getAtt(@PathVariable String id) throws IOException {
        // Download from GridFS
        // See: https://stackoverflow.com/a/32397941/12347616
        AttachmentHandler file = dm.downloadAttachment(id);
        if (file == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return ResponseEntity.ok()
                .contentLength(file.getGridFsResource().contentLength())
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .body(new InputStreamResource(file.getStream()));
    }

    @CrossOrigin(origins = "http://localhost:8081")
    @PutMapping(value = "/review/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> putReview(
            @PathVariable String id, Authentication authentication, @RequestBody Map<String, Object> json
    ){
        if (authentication == null || id == null || json == null ||
            !json.containsKey("feedback") || !json.containsKey("points")) {
            return Collections.singletonMap("ok", false);
        }

        return dm.updateReview(
            authentication.getName(), id, (String) json.get("feedback"), (List<Double>) json.get("points")
        );
    }
}
