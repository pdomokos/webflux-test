package com.example.webfluxtest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.client.reactive.ReactorResourceFactory;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.util.Collections;
import java.util.function.Function;

@Configuration
public class WebClientConfig {

    @Value("${test.host}")
    private String testHost;

//@Bean
//public WebClient webClient(){
//    return WebClient.builder()
//            .baseUrl(testHost)
//            .defaultCookie("cookieKey", "cookieValue")
//            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//            .defaultUriVariables(Collections.singletonMap("url", testHost))
//            .build();
//
//}

    @Bean
    public ReactorResourceFactory resourceFactory() {
        ConnectionProvider provider =
                ConnectionProvider.builder("test")
                        .maxConnections(5)
                        // Set custom max pending requests
                        .pendingAcquireMaxCount(10000)
                              .build();
        ReactorResourceFactory factory = new ReactorResourceFactory();
        factory.setUseGlobalResources(false);
        factory.setConnectionProvider(provider);
        return factory;
    }

    @Bean
    public WebClient webClient() {

        Function<HttpClient, HttpClient> mapper = client -> {
            // Further customizations...
            return client.baseUrl(testHost);
//            .defaultCookie("cookieKey", "cookieValue")
//            .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//            .defaultUriVariables(Collections.singletonMap("url", testHost))
        };

        ClientHttpConnector connector =
                new ReactorClientHttpConnector(resourceFactory(), mapper);

        return WebClient.builder().clientConnector(connector).build();
    }

}
