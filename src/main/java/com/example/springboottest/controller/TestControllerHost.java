package com.example.springboottest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;

@RestController
public class TestControllerHost {

    @GetMapping("/v1/get-test-string")
    public String getTest(@RequestParam long delay) {
        Instant start = Instant.now();
        for(int i=0; i<10000000; i++) {
            for(int j=0; j<100000; j++) {
            }
            if(Duration.between(start, Instant.now()).toMillis() >= delay){
                break;
            }
        }

        return "test string";
    }

}
