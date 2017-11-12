package fr.istic.m2.taa.pinit.service;

import fr.istic.m2.taa.pinit.config.ScheduledTasks;
import fr.istic.m2.taa.pinit.domain.meteo.*;
import fr.istic.m2.taa.pinit.service.exception.BadDayForWeekEndMeteo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Service
public class MeteoService {

    private static final Logger log = LoggerFactory.getLogger(MeteoService.class);


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

    public SimpleMeteo getAverageMeteoOfNextWeekEnd(double latitude, double longitude) throws BadDayForWeekEndMeteo {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(new Date());
        int numberOfDay = 0;

        while (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            calendar.add(Calendar.DATE, 1);
            numberOfDay++;
        }
        if (numberOfDay >=5){
            throw new BadDayForWeekEndMeteo("Il est trop tôt pour déterminé la météo du prochain week end");
        }

        ListMeteo meteos = getMeteo(latitude,longitude);
        List<Meteo> listMeteo = meteos.getList();

        double temperatureAverage = 0;
        double windSpeedAverage = 0;
        double rainAverage = 0;
        double snowAverage = 0;
        int nbItemMeteo = 0;

        for (Meteo meteo : listMeteo) {
            DateFormat df = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            Date currentTime = null;
            try {
                currentTime = df.parse(meteo.getDt_txt());
            } catch (ParseException e) {
                log.error("Ban date format returned from meteo api : "+meteo.getDt_txt());
                e.printStackTrace();
            }
            calendar.setTime(currentTime);

            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                    || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
                nbItemMeteo++;
                temperatureAverage += meteo.getMain().getTemp();
                windSpeedAverage += meteo.getWind().getSpeed();
                rainAverage += meteo.getRain();
                snowAverage += 0;
            }
        }

        temperatureAverage = temperatureAverage/nbItemMeteo;
        windSpeedAverage = windSpeedAverage/nbItemMeteo;
        rainAverage = rainAverage/nbItemMeteo;
        snowAverage = snowAverage/nbItemMeteo;


        SimpleMeteo averageMeteo = new SimpleMeteo();
        averageMeteo.setRain(rainAverage);
        averageMeteo.setTemperature(temperatureAverage);
        averageMeteo.setSpeedWind(windSpeedAverage);
        averageMeteo.setSnow(snowAverage);


        return averageMeteo;
    }

}
