package com.uv.projectApp.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import com.uv.projectApp.model.ParkingEvent;
import com.uv.projectApp.repository.ParkingEventRepository;
import com.uv.projectApp.services.ParkingStatusService;

@RestController
@RequestMapping("/api")
public class ParkingStatusController {

    @Autowired
    private ParkingStatusService statusService;

    @PostMapping("/evento/{id}")
    public ResponseEntity<ParkingEvent> registerEvent(@PathVariable Integer id, @RequestBody ParkingEvent event) {
        event.setParkingId(id);
        return ResponseEntity.ok(statusService.registerEvent(event));
    }

    @GetMapping("/aparcamiento/{id}/status")
    public ResponseEntity<?> getStatus(
            @PathVariable Integer id,
            @RequestParam(value = "from", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(value = "to", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to) {

        if (from != null && to != null) {
            return ResponseEntity.ok(statusService.getStatusBetween(id, from, to));
        }

        ParkingEvent latest = statusService.getLatestStatus(id);
        return latest != null ? ResponseEntity.ok(latest) : ResponseEntity.notFound().build();
    }


    @GetMapping("/status/top10")
    public ResponseEntity<List<ParkingEvent>> getTop10ByBikes() {
        return ResponseEntity.ok(statusService.getTop10LatestByParking());
    }

}

