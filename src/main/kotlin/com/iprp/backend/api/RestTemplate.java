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

    @GetMapping("/testing")
    public String sayHi() {
        return "Hi!";
    }
}
