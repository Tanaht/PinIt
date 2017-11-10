package fr.istic.m2.taa.pinit.domain.meteo;

import java.time.Instant;
import java.util.Date;
import java.util.List;

public class Meteo {
    private int dt;
    private MeteoTemperature main;
    private List<Weather> weather;
    private Cloud clouds;
    private Wind wind;
    private Rain rain;
    private Sys sys;
    private String dt_txt;
    public Meteo(){

    }

    public double getRain() {
        return rain.getRain();
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    @Override
    public String toString() {
        return "Meteo{" +
            "dt=" + dt +
            ", main=" + main +
            ", weather=" + weather +
            ", clouds=" + clouds +
            ", wind=" + wind +
            ", sys=" + sys +
            ", dt_txt='" + dt_txt + '\'' +
            '}';
    }

    public int getDt() {
        return dt;
    }

    public Date getDate(){
        Date date = Date.from( Instant.ofEpochSecond( dt ) );
        return date;

    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public MeteoTemperature getMain() {
        return main;
    }

    public void setMain(MeteoTemperature main) {
        this.main = main;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public Cloud getClouds() {
        return clouds;
    }

    public void setClouds(Cloud cloud) {
        this.clouds = cloud;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public String getDt_txt() {
        return dt_txt;
    }

    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }
}
