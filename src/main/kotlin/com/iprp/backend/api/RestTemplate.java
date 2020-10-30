package com.iprp.backend.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Simple Rest Controller for testing.
 *
 * @author Kacper Urbaniec
 * @version 2020-10-21
 */

@RestController
public class RestTemplate {

<<<<<<< HEAD
    @GetMapping("/testing")
=======
    @GetMapping("/")
>>>>>>> parent of 6b78157... Neu ohne _
    public String sayHi() {
        return "Hi!";
    }
}
