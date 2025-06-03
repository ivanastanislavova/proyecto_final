package com.uv.estacionApp.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.time.LocalDateTime;
@Document(collection = "stationReadings")
public class LecturaEstacion {

    @Id
    private String id; // MongoDB usa string ID por defecto
    @Field("station_id")
    private Integer estacionId; // Este es el ID del aparcamiento

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Integer getEstacionId() {
        return estacionId;
    }
    public void setEstacionId(Integer estacionId) {
        this.estacionId = estacionId;
    }
    public int getNitricOxides() {
        return nitricOxides;
    }
    public void setNitricOxides(int nitricOxides) {
        this.nitricOxides = nitricOxides;
    }
    public int getNitrogenDioxides() {
        return nitrogenDioxides;
    }
    public void setNitrogenDioxides(int nitrogenDioxides) {
        this.nitrogenDioxides = nitrogenDioxides;
    }
    public int getVOCs_NMHC() {
        return VOCs_NMHC;
    }
    public void setVOCs_NMHC(int vOCs_NMHC) {
        VOCs_NMHC = vOCs_NMHC;
    }
    public int getPM2_5() {
        return PM2_5;
    }
    public void setPM2_5(int pM2_5) {
        PM2_5 = pM2_5;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    @JsonProperty("nitricOxides")
    private int nitricOxides;

    @JsonProperty("nitrogenDioxides")
    private int nitrogenDioxides;

    @JsonProperty("VOCs_NMHC")
    private int VOCs_NMHC;

    @JsonProperty("PM2_5")
    private int PM2_5;

    @Field("timeStamp")
    private LocalDateTime timestamp;

}
