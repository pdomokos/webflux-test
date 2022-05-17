package com.example.webfluxtest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
public class TestControllerHost {

    @GetMapping("/v1/get-test-string-1")
    public Mono<String> getTest1(@RequestParam long delay) {
        return Mono.just("test string 1").delayElement(Duration.ofMillis(delay));
    }

    @GetMapping("/v1/get-test-string-2")
    public String getTest2(@RequestParam long delay) {
        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "test string 2";
    }

}
