package fr.istic.m2.taa.pinit.domain.meteo;

public class Cloud {

    private String all;

    public Cloud() {


    }

    @Override
    public String toString() {
        return "Cloud{" +
            "all='" + all + '\'' +
            '}';
    }

    public String getAll() {
        return all;
    }

    public void setAll(String all) {
        this.all = all;
    }
}
