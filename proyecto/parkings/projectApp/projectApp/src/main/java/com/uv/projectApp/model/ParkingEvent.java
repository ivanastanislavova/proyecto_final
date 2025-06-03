package com.uv.projectApp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
@Document(collection = "parkingEvents")
public class ParkingEvent {

    @Id
    private String id; // MongoDB usa string ID por defecto

    private Integer parkingId; // Este es el ID del aparcamiento

    private String operation;
    private int bikesAvailable;
    private int freeParkingSpots;
    private LocalDateTime timestamp;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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

    // Getters y setters...
}
