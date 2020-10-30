package com.iprp.backend.api;

import org.springframework.web.bind.annotation.*;

/**
 * Allgemeine Rest Routes
 * @author Georg Reisinger
 */

// #4
// /login => look for Spring Security tutorials

//@RestController
public class RestDefault {

    /**
     * Get Dashboard
     * @return JSON mit den Daten
     */
    @GetMapping("/")
    public String dashboard(@RequestBody String payload){
        return "{DATEN}";
    }

    /**
     * Login
     */
    @GetMapping("/login")
    public void login(@RequestBody String payload){
    }

    /**
     * Get uploadet Data
     * @return JSON mit der Abgabe
     */
    @GetMapping("/data")
    public String data(@RequestBody String payload){
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
