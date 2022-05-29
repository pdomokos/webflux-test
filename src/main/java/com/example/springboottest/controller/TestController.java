package com.example.springboottest.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
@RestController
public class TestController {


    @Qualifier("httpClient")
    private final HttpClient httpClient;

    @Qualifier("httpClient2")
    private final HttpClient httpClient2;

    @Value("${test.host}")
    private String testHost;

    @GetMapping(value = "/springboot-httpclient-test")
    public String getTestUsingHttpClient(@RequestParam long delay) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(String.format(testHost+"/v1/get-test-string?delay=%d", delay)))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    @GetMapping(value = "/springboot-httpclient-async-test")
    public CompletableFuture<String> getTestAsyncUsingHttpClient(@RequestParam long delay) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(String.format(testHost+"/v1/get-test-string?delay=%d", delay)))
                .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body);
    }


    @GetMapping(value = "/springboot-httpclient-async-test-2")
    public CompletableFuture<String> getTestAsyncUsingHttpClient2(@RequestParam long delay) {
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(String.format(testHost+"/v1/get-test-string?delay=%d", delay)))
                .build();

        return httpClient2.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body);
    }
}
