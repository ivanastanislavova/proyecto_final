package com.uv.ayuntamientoApp.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.uv.ayuntamientoApp.dto.EstacionDTO;
import com.uv.ayuntamientoApp.dto.LecturaEstacionDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class EstacionClientService {

    private final WebClient webClient;

    public EstacionClientService(@Qualifier("estacionClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<EstacionDTO> obtenerTodos() {
        return webClient.get()
                .uri("/api/estacion/estaciones")
                .retrieve()
                .bodyToFlux(EstacionDTO.class);
    }

    public Flux<LecturaEstacionDTO> obtenerEstadoEntreFechas(Integer id, LocalDateTime from, LocalDateTime to) {
        WebClient.RequestHeadersUriSpec<?> uriSpec = webClient.get();

        WebClient.RequestHeadersSpec<?> headersSpec;
        if (from != null && to != null) {
            headersSpec = uriSpec.uri(uriBuilder -> 
                uriBuilder.path("/api/estacion/estacion/{id}/status")
                        .queryParam("from", from)
                        .queryParam("to", to)
                        .build(id));
        } else {
            headersSpec = uriSpec.uri("/api/estacion/estacion/{id}/status", id);
        }

        return headersSpec.retrieve().bodyToFlux(LecturaEstacionDTO.class);
    }

    public Mono<ResponseEntity<EstacionDTO>> crearEstacion(EstacionDTO estacion, String authorizationHeader) {
        System.out.println("[EstacionClientService] Authorization header reenviado: " + authorizationHeader);
        return webClient.post()
            .uri("/api/estacion/estacion") // Corregido: endpoint correcto
            .header("X-Client-Origin", "acceso-ayuntamiento")
            .header("Authorization", authorizationHeader != null ? authorizationHeader : "")
            .bodyValue(estacion)
            .exchangeToMono(response -> {
                if (response.statusCode().is2xxSuccessful()) {
                    return response.bodyToMono(EstacionDTO.class)
                        .map(dto -> ResponseEntity.status(201).body(dto));
                } else {
                    return response.bodyToMono(String.class)
                        .flatMap(body -> Mono.error(new RuntimeException("Error en estaciones: " + body)));
                }
            });
    }

    public Mono<ResponseEntity<Object>> deleteEstacion(Integer id, String authorizationHeader) {
        return webClient.delete()
            .uri("/api/estacion/estacion/{id}", id)
            .header("Authorization", authorizationHeader)
            .exchangeToMono(response -> {
                if (response.statusCode().is2xxSuccessful()) {
                    return Mono.just(ResponseEntity.status(response.statusCode()).body(null));
                } else {
                    return response.bodyToMono(String.class)
                        .flatMap(body -> Mono.error(new RuntimeException("Error en estaciones: " + body)));
                }
            });
    }

}
