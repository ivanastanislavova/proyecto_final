package com.uv.ayuntamientoApp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AirQualityDTO {

    public AirQualityDTO(
        @JsonProperty("nitricOxides") double nitricOxides,
        @JsonProperty("nitrogenDioxides") double nitrogenDioxides,
        @JsonProperty("vocs_NMHC") double vocs_NMHC,
        @JsonProperty("pm2_5") double pm2_5
    ) {
        this.nitricOxides = nitricOxides;
        this.nitrogenDioxides = nitrogenDioxides;
        this.vocs_NMHC = vocs_NMHC;
        this.pm2_5 = pm2_5;
    }

    private double nitricOxides;
    private double nitrogenDioxides;

    @JsonProperty("vocs_NMHC")
    private double vocs_NMHC;

    @JsonProperty("pm2_5")
    private double pm2_5;

    public double getNitricOxides() {
        return nitricOxides;
    }

    public void setNitricOxides(double nitricOxides) {
        this.nitricOxides = nitricOxides;
    }

    public double getNitrogenDioxides() {
        return nitrogenDioxides;
    }

    public void setNitrogenDioxides(double nitrogenDioxides) {
        this.nitrogenDioxides = nitrogenDioxides;
    }

    public double getVocs_NMHC() {
        return vocs_NMHC;
    }

    public void setVocs_NMHC(double vocs_NMHC) {
        this.vocs_NMHC = vocs_NMHC;
    }

    public double getPm2_5() {
        return pm2_5;
    }

    public void setPm2_5(double pm2_5) {
        this.pm2_5 = pm2_5;
    }

    // Getters y Setters
}
