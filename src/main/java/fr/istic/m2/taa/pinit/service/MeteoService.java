package fr.istic.m2.taa.pinit.service;

import fr.istic.m2.taa.pinit.domain.meteo.ListMeteo;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class MeteoService {

    private final static String apiKey ="84d3db10b53efd40a935242ef5fa2091";
    private final static String urlApi = "http://api.openweathermap.org/data/2.5/forecast";

    public MeteoService(){

    }

    public ListMeteo getMeteo(double latitude, double longitude){

        String url = urlApi+"?lat="+latitude+"&lon="+longitude+"&lang=fr&units=metric+&appid="+apiKey;

        RestTemplate restTemplate = new RestTemplate();

        ListMeteo resApi = restTemplate.getForObject(url, ListMeteo.class);

        return resApi;
    }

}
