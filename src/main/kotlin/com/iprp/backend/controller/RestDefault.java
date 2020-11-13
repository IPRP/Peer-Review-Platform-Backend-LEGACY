package com.iprp.backend.controller;

import org.springframework.web.bind.annotation.*;

@RestController
public class RestDefault {

    /**
     * Get Dashboard
     * @return JSON mit den Daten

    @GetMapping("/")
    public String dashboard(@RequestParam(value = "payload") String payload){
        return "{DATEN}";
    }

    /**
     * Login

     @GetMapping("/login")
     public void login(@RequestParam(value = "payload") String payload){
     }
     */

    /**
     * Get uploadet Data
     * @return JSON mit der Abgabe
     */
    @GetMapping("/data")
    public String data(){
        return "{DATEN}";
    }

    /**
     * Upload Data
     */
    @PostMapping("/data")
    public void postdata(@RequestBody String payload){
    }

    /**
     * Edit Data
     */
    @PutMapping("/data")
    public void putdata(@RequestBody String payload){
    }

    /**
     * Delete Data
     */
    @DeleteMapping("/data")
    public void deldata(@RequestBody String payload){
    }
}
