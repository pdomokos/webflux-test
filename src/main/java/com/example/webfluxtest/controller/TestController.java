package com.example.webfluxtest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@RestController
public class TestController {

    private final WebClient webClient;

    @Qualifier("httpClient")
    private final HttpClient httpClient;

    @Qualifier("httpClient2")
    private final HttpClient httpClient2;

    @GetMapping(value = "/webflux-webclient-test")
    public Mono<String> getTestUsingWebfluxWebclient(@RequestParam long delay) {
        return webClient.get().uri("http://192.168.0.59:8080/v1/get-test-string-1?delay={delay}", delay)
                .retrieve()
                .bodyToMono(String.class);
    }

    @GetMapping(value = "/springboot-httpclient-test")
    public String getTestUsingHttpClient(@RequestParam long delay) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(String.format("http://192.168.0.59:8080/v1/get-test-string-2?delay=%d", delay)))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    @GetMapping(value = "/springboot-httpclient-async-test")
    public CompletableFuture<String> getTestAsyncUsingHttpClient(@RequestParam long delay) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(String.format("http://192.168.0.59:8080/v1/get-test-string-2?delay=%d", delay)))
                .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body);
    }

    @GetMapping(value = "/springboot-httpclient-test-2")
    public String getTestUsingHttpClient2(@RequestParam long delay) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(String.format("http://192.168.0.59:8080/v1/get-test-string-2?delay=%d", delay)))
                .build();

        HttpResponse<String> response = httpClient2.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    @GetMapping(value = "/springboot-httpclient-async-test-2")
    public CompletableFuture<String> getTestAsyncUsingHttpClient2(@RequestParam long delay) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(String.format("http://192.168.0.59:8080/v1/get-test-string-2?delay=%d", delay)))
                .build();

        return httpClient2.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body);
    }
}
