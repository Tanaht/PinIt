package fr.istic.m2.taa.pinit.domain.meteo;

public class Sys {
    private String pod;

    @Override
    public String toString() {
        return "Sys{" +
            "pod='" + pod + '\'' +
            '}';
    }

    public String getPod() {
        return pod;
    }

    public void setPod(String pod) {
        this.pod = pod;
    }

    public Sys() {
    }
}
