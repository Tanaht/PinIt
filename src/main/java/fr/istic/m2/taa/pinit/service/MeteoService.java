package fr.istic.m2.taa.pinit.service;

import fr.istic.m2.taa.pinit.domain.meteo.CoordGps;
import fr.istic.m2.taa.pinit.domain.meteo.ListMeteo;
import fr.istic.m2.taa.pinit.domain.meteo.Meteo;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
public class MeteoService {

    private final static String apiKey ="84d3db10b53efd40a935242ef5fa2091";
    private final static String urlApi = "http://api.openweathermap.org/data/2.5/forecast?appid="+apiKey+"&units=metric";

    public MeteoService(){

    }

    public ListMeteo getMeteo(double latitude, double longitude){

        String url = urlApi+"&lat="+latitude+"&lon="+longitude;

        RestTemplate restTemplate = new RestTemplate();

        ListMeteo resApi = restTemplate.getForObject(url, ListMeteo.class);

        return resApi;
    }

    public ListMeteo getAverageMeteoOfNextWeekEnd(double latitude, double longitude) throws Exception {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date());
        int numberOfDay = 0;

        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            calendar.add(Calendar.DATE, 1);
            numberOfDay++;
        }
        if (numberOfDay >=5){
            throw new Exception("Il est trop tôt pour déterminé la météo du prochain week end");
        }

        ListMeteo meteos = getMeteo(latitude,longitude);
        List<Meteo> listMeteo = meteos.getList();

        double temperatureAverage = 0;
        double cloudAverage = 0;
        double windSpeedAverage = 0;
        double rainAverage = 0;
        double snowAverage = 0;
        int nbItemMeteo = 0;

        for (Meteo meteo : listMeteo) {
            DateFormat df = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            Date currentTime =  df.parse(meteo.getDt_txt());
            calendar.setTime(currentTime);

            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                    || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                nbItemMeteo++;
                temperatureAverage += meteo.getMain().getTemp();
                cloudAverage += meteo.getClouds().getAll();
                windSpeedAverage += meteo.getWind().getSpeed();
                rainAverage += meteo.getRain();
                snowAverage += 0;
            }
        }

        temperatureAverage = temperatureAverage/nbItemMeteo;
        cloudAverage = cloudAverage/nbItemMeteo;
        windSpeedAverage = windSpeedAverage/nbItemMeteo;
        rainAverage = rainAverage/nbItemMeteo;
        snowAverage = snowAverage/nbItemMeteo;

        return null;
    }

}
