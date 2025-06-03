package com.uv.ayuntamientoApp.service;

import com.uv.ayuntamientoApp.dto.AggregatedDataSnapshotDTO;
import com.uv.ayuntamientoApp.model.AggregatedDataSnapshotEntity;
import com.uv.ayuntamientoApp.repository.AggregatedDataSnapshotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AggregatedDataStorageService {

    @Autowired
    private AggregatedDataService aggregatedDataService;

    @Autowired
    private AggregatedDataSnapshotRepository repository;

    public Mono<AggregatedDataSnapshotDTO> aggregateAndStore() {
        return aggregatedDataService.obtenerDatosAgregados()
            .map(snapshotDto -> {
                AggregatedDataSnapshotEntity entity = new AggregatedDataSnapshotEntity();
                entity.setTimeStamp(snapshotDto.getTimeStamp());
                entity.setAggregatedData(snapshotDto.getAggregatedData());
                repository.save(entity);  // Guardamos en MongoDB
                return snapshotDto;       // Retornamos el DTO para respuesta
            });
    }

    public AggregatedDataSnapshotDTO getLatestSnapshot() {
        AggregatedDataSnapshotEntity entity = repository.findFirstByOrderByTimeStampDesc();
        if (entity == null) return null;

        AggregatedDataSnapshotDTO dto = new AggregatedDataSnapshotDTO();
        dto.setTimeStamp(entity.getTimeStamp());
        dto.setAggregatedData(entity.getAggregatedData());
        return dto;
    }
}
