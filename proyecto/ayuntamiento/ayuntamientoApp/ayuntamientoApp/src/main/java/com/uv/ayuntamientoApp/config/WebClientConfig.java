package com.uv.ayuntamientoApp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${apiclient.estacion-url}")
    private String estacionUrl;

    @Value("${apiclient.aparcamiento-url}")
    private String aparcamientoUrl;

    @Bean(name = "parkingClient")
    public WebClient parkingClient() {
        return WebClient.builder().baseUrl(aparcamientoUrl).build();
    }

    @Bean(name = "estacionClient")
    public WebClient estacionClient() {
        return WebClient.builder().baseUrl(estacionUrl).build();
    }
}
