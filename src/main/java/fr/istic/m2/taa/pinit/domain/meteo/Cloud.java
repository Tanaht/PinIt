package fr.istic.m2.taa.pinit.domain.meteo;

public class Cloud {

    private int all;

    public Cloud() {


    }

    @Override
    public String toString() {
        return "Cloud{" +
            "all='" + all + '\'' +
            '}';
    }

    public int getAll() {
        return all;
    }

    public void setAll(int all) {
        this.all = all;
    }
}
