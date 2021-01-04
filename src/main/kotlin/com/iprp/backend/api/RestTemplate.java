package com.iprp.backend.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @GetMapping(value = "/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getJson() {
        //return Collections.singletonMap("response", "baum");
        //return Collections.singletonMap("response", new ArrayList<String> (List.of("a", "b")));
        Map<String, Integer> map = Stream.of(new Object[][] {
                { "data1", 1 },
                { "data2", 2 },
        }).collect(Collectors.toMap(data -> (String) data[0], data -> (Integer) data[1]));
        return Collections.singletonMap("response", map);
    }
}
