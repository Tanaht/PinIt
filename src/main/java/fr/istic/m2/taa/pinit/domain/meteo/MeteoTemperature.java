package fr.istic.m2.taa.pinit.domain.meteo;

public class MeteoTemperature {
    private double temp;
    private double temp_min;
    private double temp_max;
    private double sea_level;
    private double grnd_level;
    private double humidity;
    private double temp_kf;

    public MeteoTemperature() {

    }

    @Override
    public String toString() {
        return "MeteoTemperature{" +
            "temp=" + temp +
            ", temp_min=" + temp_min +
            ", temp_max=" + temp_max +
            ", sea_level=" + sea_level +
            ", grnd_level=" + grnd_level +
            ", humidity=" + humidity +
            ", temp_kf=" + temp_kf +
            '}';
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(double temp_min) {
        this.temp_min = temp_min;
    }

    public double getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(double temp_max) {
        this.temp_max = temp_max;
    }

    public double getSea_level() {
        return sea_level;
    }

    public void setSea_level(double sea_level) {
        this.sea_level = sea_level;
    }

    public double getGrnd_level() {
        return grnd_level;
    }

    public void setGrnd_level(double grnd_level) {
        this.grnd_level = grnd_level;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getTemp_kf() {
        return temp_kf;
    }

    public void setTemp_kf(double temp_kf) {
        this.temp_kf = temp_kf;
    }
}
