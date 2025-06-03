package com.uv.estacionApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import com.uv.estacionApp.model.LecturaEstacion;
import com.uv.estacionApp.repository.LecturaEstacionRepository;
import com.uv.estacionApp.service.LecturaEstacionService;

@RestController
@RequestMapping("/api/estacion")
public class LecturaEstacionController {

    @Autowired
    private LecturaEstacionService statusService;

    @PostMapping("/evento/{id}")
    public ResponseEntity<LecturaEstacion> registerEvent(@PathVariable Integer id, @RequestBody LecturaEstacion event) {
        event.setEstacionId(id);
        return ResponseEntity.ok(statusService.registerEvent(event));
    }

    @GetMapping("/estacion/{id}/status")
    public ResponseEntity<?> getStatus(
            @PathVariable Integer id,
            @RequestParam(value = "from", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(value = "to", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {

        if (from != null && to != null) {
            return ResponseEntity.ok(statusService.getStatusBetween(id, from, to));
        }

        LecturaEstacion latest = statusService.getLatestStatus(id);
        return latest != null ? ResponseEntity.ok(latest) : ResponseEntity.notFound().build();
    }

}

