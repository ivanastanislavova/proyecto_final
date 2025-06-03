package com.uv.ayuntamientoApp.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.uv.ayuntamientoApp.dto.ParkingDTO;
import com.uv.ayuntamientoApp.dto.ParkingEventDTO;

import jakarta.servlet.http.HttpServletRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ParkingClientService {

    private final WebClient webClient;

    public ParkingClientService(@Qualifier("parkingClient") WebClient webClient) {
        this.webClient = webClient;
    }

    public Flux<ParkingDTO> obtenerParkings() {
        return webClient.get()
                .uri("/api/aparcamientos")
                .retrieve()
                .bodyToFlux(ParkingDTO.class);
    }

    public Flux<ParkingEventDTO> obtenerEstadoEntreFechas(Integer id, LocalDateTime from, LocalDateTime to) {
        WebClient.RequestHeadersUriSpec<?> uriSpec = webClient.get();

        WebClient.RequestHeadersSpec<?> headersSpec;
        if (from != null && to != null) {
            headersSpec = uriSpec.uri(uriBuilder -> 
                uriBuilder.path("/api/aparcamiento/{id}/status")
                        .queryParam("from", from)
                        .queryParam("to", to)
                        .build(id));
        } else {
            headersSpec = uriSpec.uri("/api/aparcamiento/{id}/status", id);
        }

        return headersSpec.retrieve().bodyToFlux(ParkingEventDTO.class);
    }

    // SERVICE ENDPOINTS DE ADMIN
    public Mono<ResponseEntity<ParkingDTO>> crearParking(ParkingDTO parking, HttpServletRequest request){
        String authHeader = request.getHeader("Authorization");
        String refererHeader = request.getHeader("Referer");
        return webClient.post()
            .uri("/api/aparcamiento")
            .header("X-Client-Origin", "acceso-ayuntamiento")
            .header("Authorization", authHeader != null ? authHeader : "")
            .bodyValue(parking)
            .exchangeToMono(response -> {
                System.out.println("[ParkingClientService] Código HTTP recibido de projectApp: " + response.statusCode());
                if (response.statusCode().is2xxSuccessful()) {
                    System.out.println("[ParkingClientService] Entró en el if de 2xxSuccessful");
                    return response.bodyToMono(ParkingDTO.class)
                            .map(dto -> ResponseEntity.status(HttpStatus.CREATED).body(dto));
                } else {
                    System.out.println("[ParkingClientService] Entró en el else (no 2xx) con código: " + response.statusCode());
                    System.out.println("[ParkingClientService] Referer header: " + refererHeader);
                    return response.bodyToMono(String.class)
                            .flatMap(body -> Mono.error(new RuntimeException("Error en parkings: " + body)));
                }
            });
    }

    public Mono<ResponseEntity<Object>> deleteParking(Integer id, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String refererHeader = request.getHeader("Referer");
        return webClient.delete()
            .uri("/api/aparcamiento/{id}", id)
            .header("X-Client-Origin", "acceso-ayuntamiento")
            .header("Authorization", authHeader != null ? authHeader : "")
            .exchangeToMono(response -> {
                System.out.println("[ParkingClientService] Código HTTP recibido de projectApp (DELETE): " + response.statusCode());
                if (response.statusCode().is2xxSuccessful()) {
                    return Mono.just(ResponseEntity.status(HttpStatus.NO_CONTENT).body(null));
                } else {
                    return response.bodyToMono(String.class)
                        .flatMap(body -> Mono.error(new RuntimeException("Error en parkings: " + body)));
                }
            });
    }


}
