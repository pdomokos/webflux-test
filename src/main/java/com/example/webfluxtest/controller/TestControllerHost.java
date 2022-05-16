package com.example.webfluxtest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class TestControllerHost {

    @GetMapping("/v1/get-test-string")
    public Mono<String> getUserWithDelay() {
        return Mono.just("test string 2");
    }
}
