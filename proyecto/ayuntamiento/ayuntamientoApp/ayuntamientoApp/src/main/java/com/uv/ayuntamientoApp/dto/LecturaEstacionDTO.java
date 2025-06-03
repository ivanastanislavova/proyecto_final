package com.uv.ayuntamientoApp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class LecturaEstacionDTO {

    private String id;

    private Integer estacionId;

    @JsonProperty("nitricOxides")
    private int nitricOxides;

    @JsonProperty("nitrogenDioxides")
    private int nitrogenDioxides;

    @JsonProperty("VOCs_NMHC")
    private int VOCs_NMHC;

    @JsonProperty("PM2_5")
    private int PM2_5;

    private LocalDateTime timestamp;

    // Getters y Setters
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

    public void setVOCs_NMHC(int VOCs_NMHC) {
        this.VOCs_NMHC = VOCs_NMHC;
    }

    public int getPM2_5() {
        return PM2_5;
    }

    public void setPM2_5(int PM2_5) {
        this.PM2_5 = PM2_5;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
