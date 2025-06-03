package com.uv.ayuntamientoApp.controller;

import com.uv.ayuntamientoApp.dto.AggregatedDataSnapshotDTO;
import com.uv.ayuntamientoApp.service.AggregatedDataService;
import com.uv.ayuntamientoApp.service.AggregatedDataStorageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/ayuntamiento")
public class AggregatedDataController {

    @Autowired
    private AggregatedDataService aggregatedDataService;

    @Autowired
    private AggregatedDataStorageService storageService;

    @GetMapping("/aggregateData")
    public Mono<AggregatedDataSnapshotDTO> aggregateAndStore() {
        return storageService.aggregateAndStore();
    }

    @GetMapping("/aggregatedData")
    public ResponseEntity<AggregatedDataSnapshotDTO> getLatestAggregatedData() {
        AggregatedDataSnapshotDTO latest = storageService.getLatestSnapshot();
        return latest != null ? ResponseEntity.ok(latest) : ResponseEntity.notFound().build();
    }
}
