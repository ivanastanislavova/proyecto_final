package com.uv.ayuntamientoApp.repository;

import com.uv.ayuntamientoApp.model.AggregatedDataSnapshotEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AggregatedDataSnapshotRepository extends MongoRepository<AggregatedDataSnapshotEntity, String> {
    AggregatedDataSnapshotEntity findFirstByOrderByTimeStampDesc();
}
