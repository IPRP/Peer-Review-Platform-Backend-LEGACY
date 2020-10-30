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
    public String restgetdashboard(int id){
        return "{DATEN}";
    }

    /**
     * Login
     * @param id Userid
     * @param pw User passwort
     */
    @GetMapping("/login")
    public void restgetlogin(int id, String pw){
    }

    /**
     * Get uploadet Data
     * @param id deliveryid
     * @return JSON mit der Abgabe
     */
    @GetMapping("/data")
    public String restgetdata(int id){
        return "{DATEN}";
    }

    /**
     * Upload Data
     * @param id deliveryid
     */
    @PostMapping("/data")
    public void restpostdata(int id){
    }

    /**
     * Edit Data
     * @param id deliveryid
     */
    @PutMapping("/data")
    public void restputdata(int id){
    }

    /**
     * Delete Data
     * @param id deliveryid
     */
    @DeleteMapping("/data")
    public void restdeldata(int id){
    }
}
