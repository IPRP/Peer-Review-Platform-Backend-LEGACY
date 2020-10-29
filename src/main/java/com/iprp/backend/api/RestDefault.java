package com.iprp.backend.api;

import org.springframework.web.bind.annotation.*;

/**
 * Allgemeine Rest Routes
 * @author Georg Reisinger
 */

// TODO @Georg
// #1
// Move Java Classes (src/main/java) to Kotlin (src/main/kotlin) => mixing is allowed!
// See RestTemplate in api

// #2
// Rename methods to Java Notation rest_get_dashboard => dashboard
// rest_get => information is redundant

// #3
// Map parameters correctly
// Use PathParams
// General info: https://www.baeldung.com/spring-requestparam-vs-pathvariable
// Json specific: https://stackoverflow.com/a/29330280/12347616

// #4
// /login => look for Spring Security tutorials

//@RestController
public class RestDefault {

    /**
     * Get Dashboard
     * @param id User id
     * @return JSON mit den Daten
     */
    @GetMapping("/")
    public String rest_get_dashboard(int id){
        return "{DATEN}";
    }

    /**
     * Login
     * @param id Userid
     * @param pw User passwort
     */
    @GetMapping("/login")
    public void rest_get_login(int id, String pw){
    }

    /**
     * Get uploadet Data
     * @param id deliveryid
     * @return JSON mit der Abgabe
     */
    @GetMapping("/data")
    public String rest_get_data(int id){
        return "{DATEN}";
    }

    /**
     * Upload Data
     * @param id deliveryid
     */
    @PostMapping("/data")
    public void rest_post_data(int id){
    }

    /**
     * Edit Data
     * @param id deliveryid
     */
    @PutMapping("/data")
    public void rest_put_data(int id){
    }

    /**
     * Delete Data
     * @param id deliveryid
     */
    @DeleteMapping("/data")
    public void rest_del_data(int id){
    }
}
