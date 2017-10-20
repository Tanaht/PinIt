package fr.istic.m2.taa.pinit.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Activity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nameActivity;

    //température min max en celsius
    private double tempMin;
    private double tempMax;

    //vitesse du vend metre/sec
    private double windSpeedMin;
    private double windSpeedMax;

    //pluie toléré, en mm sur 3 heure
    private double rainMin;
    private double rainMax;

    //Volume de neige durant les 3 dernière heures
    private double snowMin;
    private double snowMax;

    public Activity(){

    }

    public String getNameActivity() {
        return nameActivity;
    }

    public void setNameActivity(String nameActivity) {
        this.nameActivity = nameActivity;
    }

    public double getTempMin() {
        return tempMin;
    }

    public void setTempMin(double tempMin) {
        this.tempMin = tempMin;
    }

    public double getTempMax() {
        return tempMax;
    }

    public void setTempMax(double tempMax) {
        this.tempMax = tempMax;
    }

    public double getWindSpeedMin() {
        return windSpeedMin;
    }

    public void setWindSpeedMin(double windSpeedMin) {
        this.windSpeedMin = windSpeedMin;
    }

    public double getWindSpeedMax() {
        return windSpeedMax;
    }

    public void setWindSpeedMax(double windSpeedMax) {
        this.windSpeedMax = windSpeedMax;
    }

    public double getRainMin() {
        return rainMin;
    }

    public void setRainMin(double rainMin) {
        this.rainMin = rainMin;
    }

    public double getRainMax() {
        return rainMax;
    }

    public void setRainMax(double rainMax) {
        this.rainMax = rainMax;
    }

    public double getSnowMin() {
        return snowMin;
    }

    public void setSnowMin(double snowMin) {
        this.snowMin = snowMin;
    }

    public double getSnowMax() {
        return snowMax;
    }

    public void setSnowMax(double snowMax) {
        this.snowMax = snowMax;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
