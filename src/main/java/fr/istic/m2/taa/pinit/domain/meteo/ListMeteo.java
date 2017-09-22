package fr.istic.m2.taa.pinit.domain.meteo;


import java.util.List;


public class ListMeteo {

    private String cod;
    private String message;
    private int cnt;
    private List<Meteo> list;
    private City city;

    public ListMeteo(){

    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public List<Meteo> getList() {
        return list;
    }

    public void setList(List<Meteo> list) {
        this.list = list;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "ListMeteo{" +
            "cod='" + cod + '\'' +
            ", message='" + message + '\'' +
            ", cnt=" + cnt +
            ", list=" + list +
            ", city=" + city +
            '}';
    }
}
