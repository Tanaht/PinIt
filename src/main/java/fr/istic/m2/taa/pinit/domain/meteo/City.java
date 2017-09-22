package fr.istic.m2.taa.pinit.domain.meteo;

public class City {
    private String id;
    private String name;
    private CoordGps coord;
    private String country;
    private int population;

    public City() {

    }

    @Override
    public String toString() {
        return "City{" +
            "id='" + id + '\'' +
            ", name='" + name + '\'' +
            ", coord=" + coord +
            ", country='" + country + '\'' +
            ", population=" + population +
            '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CoordGps getCoord() {
        return coord;
    }

    public void setCoord(CoordGps coord) {
        this.coord = coord;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }
}
