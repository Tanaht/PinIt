package fr.istic.m2.taa.pinit.domain.meteo;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Rain {

    @JsonProperty("3h")
    private double rain;

    public Rain(double rain) {
        this.rain = rain;
    }

    public double getRain() {
        return rain;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }
}
