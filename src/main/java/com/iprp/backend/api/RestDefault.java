package com.iprp.backend.api;

import org.springframework.web.bind.annotation.*;

/**
 * Allgemeine Rest Routes
 * @author Georg Reisinger
 */
@RestController
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
