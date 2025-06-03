package com.uv.ayuntamientoApp.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uv.ayuntamientoApp.dto.ParkingDTO;
import com.uv.ayuntamientoApp.dto.ParkingEventDTO;
import com.uv.ayuntamientoApp.service.ParkingClientService;

import jakarta.servlet.http.HttpServletRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/ayuntamiento")
public class ParkingController {

    private final ParkingClientService parkingClientService;

    @Autowired
    public ParkingController(ParkingClientService parkingClientService) {
        this.parkingClientService = parkingClientService;
    }

    @GetMapping("/parkings")
    public Flux<ParkingDTO> getAllParkings() {
        return parkingClientService.obtenerParkings();
    }
    
    @GetMapping("/parking/{id}")
    public Flux<ParkingEventDTO> getEstadoEntreFechas(
            @PathVariable Integer id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {
        return parkingClientService.obtenerEstadoEntreFechas(id, from, to);
    }

    // ENDPOINTS DE ADMIN
    @PostMapping("/admin/new-parking")
    public Mono<ResponseEntity<Object>> createParking(@RequestBody ParkingDTO parking, HttpServletRequest request){
        return parkingClientService.crearParking(parking, request)
            .map(resp -> ResponseEntity.status(resp.getStatusCode()).body((Object) resp.getBody()))
            .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body((Object) e.getMessage())));
    }

    @DeleteMapping("/admin/delete-parking/{id}")
    public Mono<ResponseEntity<Object>> deleteParking(@PathVariable Integer id, HttpServletRequest request) {
        return parkingClientService.deleteParking(id, request)
            .map(resp -> ResponseEntity.status(resp.getStatusCode()).body((Object) resp.getBody()))
            .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.BAD_REQUEST).body((Object) e.getMessage())));
    }

}