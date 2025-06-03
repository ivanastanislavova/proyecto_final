package com.uv.ayuntamientoApp.model;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

// AggregatedDataSnapshotEntity.java
import com.uv.ayuntamientoApp.dto.AggregatedDataSnapshotDTO;

@Document(collection = "aggregatedData")
public class AggregatedDataSnapshotEntity {
    @Id
    private String id;
    private LocalDateTime timeStamp;
    private List<AggregatedDataSnapshotDTO.AparcamientoData> aggregatedData;

    // Getters y setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public List<AggregatedDataSnapshotDTO.AparcamientoData> getAggregatedData() {
        return aggregatedData;
    }

    public void setAggregatedData(List<AggregatedDataSnapshotDTO.AparcamientoData> aggregatedData) {
        this.aggregatedData = aggregatedData;
    }
}
