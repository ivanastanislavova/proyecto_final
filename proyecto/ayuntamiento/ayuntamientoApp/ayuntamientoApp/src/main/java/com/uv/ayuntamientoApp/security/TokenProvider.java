package com.uv.ayuntamientoApp.security;


import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value; 

@Service
public class TokenProvider {

    private final WebClient webClient;
    private String token;

    @PostConstruct
    public void init() {
        System.out.println(">>> Token generado al arrancar: " + token);
    }

    public TokenProvider(@Value("${apiclient.autenticacion-url}") String authUrl,
                         @Value("${apiclient.autenticacion-subject}") String subject,
                         @Value("${apiclient.autenticacion-role}") String role) {
        this.webClient = WebClient.builder().baseUrl(authUrl).build();
        this.token = webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/auth/token")
                        .queryParam("subject", subject)
                        .queryParam("role", role)
                        .build())
                .retrieve()
                .bodyToMono(String.class)
                .block(); // Sincrónico solo al arranque
    }

    public String getToken() {
        return token;
    }
}
