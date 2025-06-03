package com.uv.ayuntamientoApp.controller;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uv.ayuntamientoApp.dto.EstacionDTO;
import com.uv.ayuntamientoApp.dto.LecturaEstacionDTO;
import com.uv.ayuntamientoApp.service.EstacionClientService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/ayuntamiento")
public class EstacionController{

    private final EstacionClientService service;

    public EstacionController(EstacionClientService service){
        this.service = service;
    }

    @GetMapping("/estaciones")
    public Flux<EstacionDTO> getAllEstaciones() {
        return service.obtenerTodos();
    }
    
    @GetMapping("/estacion/{id}")
    public Flux<LecturaEstacionDTO> getEstadoEntreFechas(
            @PathVariable Integer id,
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to) {
        return service.obtenerEstadoEntreFechas(id, from, to);
    }

    @PostMapping("/admin/new-estacion")
    public Mono<ResponseEntity<EstacionDTO>> crearEstacion(
            @RequestBody EstacionDTO estacion,
            @RequestHeader("Authorization") String authorizationHeader) {
        System.out.println("[EstacionController] Authorization header recibido: " + authorizationHeader);
        return service.crearEstacion(estacion, authorizationHeader);
    }

    @DeleteMapping("/admin/delete-estacion/{id}")
    public Mono<ResponseEntity<Object>> deleteEstacion(
            @PathVariable Integer id,
            @RequestHeader("Authorization") String authorizationHeader) {
        return service.deleteEstacion(id, authorizationHeader)
            .map(resp -> ResponseEntity.status(resp.getStatusCode()).body(resp.getBody()))
            .onErrorResume(e -> Mono.just(ResponseEntity.status(400).body(e.getMessage())));
    }
}


