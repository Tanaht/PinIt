package fr.istic.m2.taa.pinit.domain.meteo;

public class Wind {

    private double speed;
    private double deg;

    public Wind() {
    }

    @Override
    public String toString() {
        return "Wind{" +
            "speed=" + speed +
            ", deg=" + deg +
            '}';
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDeg() {
        return deg;
    }

    public void setDeg(double deg) {
        this.deg = deg;
    }
}
