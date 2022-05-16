package com.example.webfluxtest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class TestController {

    private final WebClient webClient;

    @GetMapping(value = "/webflux-webclient-test")
    public Mono<String> getTestUsingWebfluxWebclient() {
        return webClient.get().uri(URI.create("http://192.168.0.59:8080/v1/get-test-string"))
                .retrieve()
                .bodyToMono(String.class);
    }
}
