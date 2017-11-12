package fr.istic.m2.taa.pinit.domain.meteo;

public class Cloud {

    private double all;

    public Cloud() {

    }

    public Cloud(double all) {
        this.all = all;
    }

    @Override
    public String toString() {
        return "Cloud{" +
            "all='" + all + '\'' +
            '}';
    }

    public double getAll() {
        return all;
    }

    public void setAll(double all) {
        this.all = all;
    }
}
