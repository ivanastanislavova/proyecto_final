package com.uv.ayuntamientoApp.dto;


import java.time.LocalDateTime;

public class ParkingEventDTO {
    private Integer parkingId;
    private String operation;
    private int bikesAvailable;
    private int freeParkingSpots;
    private LocalDateTime timestamp;

    public ParkingEventDTO() {}

    public ParkingEventDTO(Integer parkingId, String operation, int bikesAvailable, int freeParkingSpots, LocalDateTime timestamp) {
        this.parkingId = parkingId;
        this.operation = operation;
        this.bikesAvailable = bikesAvailable;
        this.freeParkingSpots = freeParkingSpots;
        this.timestamp = timestamp;
    }

    public Integer getParkingId() {
        return parkingId;
    }

    public void setParkingId(Integer parkingId) {
        this.parkingId = parkingId;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public int getBikesAvailable() {
        return bikesAvailable;
    }

    public void setBikesAvailable(int bikesAvailable) {
        this.bikesAvailable = bikesAvailable;
    }

    public int getFreeParkingSpots() {
        return freeParkingSpots;
    }

    public void setFreeParkingSpots(int freeParkingSpots) {
        this.freeParkingSpots = freeParkingSpots;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
