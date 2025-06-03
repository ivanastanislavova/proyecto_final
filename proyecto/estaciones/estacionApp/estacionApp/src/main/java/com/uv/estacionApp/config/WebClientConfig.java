package com.uv.estacionApp.config;

import com.uv.estacionApp.security.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient(TokenProvider tokenProvider) {
        System.out.println(">>> Web Clienttt");
        return WebClient.builder()
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + tokenProvider.getToken())
                .build();
    }
}
