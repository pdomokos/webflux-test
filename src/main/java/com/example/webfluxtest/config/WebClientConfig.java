package com.example.webfluxtest.config;

import io.netty.handler.ssl.SslContextBuilder;
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
import reactor.netty.resources.LoopResources;

import java.time.Duration;
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
                        .maxConnections(45)
                        // Set custom max pending requests
                        .pendingAcquireMaxCount(4000)
                              .build();
        LoopResources loop = LoopResources.create("kl-event-loop", 1, 4, true);
        ReactorResourceFactory factory = new ReactorResourceFactory();
        factory.setUseGlobalResources(false);
        factory.setConnectionProvider(provider);
        factory.setLoopResources(loop);
        return factory;
    }

    @Bean
    public WebClient webClient() {

        Function<HttpClient, HttpClient> mapper = client -> client.baseUrl(testHost);

        ClientHttpConnector connector =
                new ReactorClientHttpConnector(resourceFactory(), mapper);

        return WebClient.builder().clientConnector(connector).build();

    }

}
