package com.uv.ayuntamientoApp.dto;

// AggregatedDataSnapshotDTO.java
import java.time.LocalDateTime;
import java.util.List;

public class AggregatedDataSnapshotDTO {

    private LocalDateTime timeStamp;
    private List<AparcamientoData> aggregatedData;

    public AggregatedDataSnapshotDTO() {}

    public AggregatedDataSnapshotDTO(LocalDateTime timeStamp, List<AparcamientoData> aggregatedData) {
        this.timeStamp = timeStamp;
        this.aggregatedData = aggregatedData;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public List<AparcamientoData> getAggregatedData() {
        return aggregatedData;
    }

    public void setAggregatedData(List<AparcamientoData> aggregatedData) {
        this.aggregatedData = aggregatedData;
    }

    public static class AparcamientoData {
        private int id;
        private double averageBikesAvailable;
        private AirQualityDTO airQuality;

        public AparcamientoData() {}

        public AparcamientoData(int id, double averageBikesAvailable, AirQualityDTO airQuality) {
            this.id = id;
            this.averageBikesAvailable = averageBikesAvailable;
            this.airQuality = airQuality;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public double getAverageBikesAvailable() {
            return averageBikesAvailable;
        }

        public void setAverageBikesAvailable(double averageBikesAvailable) {
            this.averageBikesAvailable = averageBikesAvailable;
        }

        public AirQualityDTO getAirQuality() {
            return airQuality;
        }

        public void setAirQuality(AirQualityDTO airQuality) {
            this.airQuality = airQuality;
        }
    }
}
