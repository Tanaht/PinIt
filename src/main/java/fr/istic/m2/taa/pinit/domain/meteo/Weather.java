package fr.istic.m2.taa.pinit.domain.meteo;

public class Weather {
    private String id;
    private String main;
    private String description;
    private String icon;

    public Weather() {

    }

    @Override
    public String toString() {
        return "Weather{" +
            "id='" + id + '\'' +
            ", main='" + main + '\'' +
            ", String='" + description + '\'' +
            ", icon='" + icon + '\'' +
            '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String string) {
        description = string;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
