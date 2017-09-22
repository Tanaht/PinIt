package fr.istic.m2.taa.pinit.domain.meteo;

public class CoordGps {
    private double longitude;
    private double latitute;

    public CoordGps() {
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitute() {
        return latitute;
    }

    public void setLatitute(double latitute) {
        this.latitute = latitute;
    }

    @Override
    public String toString() {
        return "CoordGps{" +
            "longitude=" + longitude +
            ", latitute=" + latitute +
            '}';
    }
}
