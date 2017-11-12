package fr.istic.m2.taa.pinit.domain.meteo;

public class SimpleMeteo {

    private double temperature;
    private double speedWind;
    private double rain;
    private double snow;

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getSpeedWind() {
        return speedWind;
    }

    public void setSpeedWind(double speedWind) {
        this.speedWind = speedWind;
    }

    public double getRain() {
        return rain;
    }

    public void setRain(double rain) {
        this.rain = rain;
    }

    public double getSnow() {
        return snow;
    }

    public void setSnow(double snow) {
        this.snow = snow;
    }
}
