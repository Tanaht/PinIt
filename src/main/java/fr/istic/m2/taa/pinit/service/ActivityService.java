package fr.istic.m2.taa.pinit.service;

import fr.istic.m2.taa.pinit.domain.Activity;
import fr.istic.m2.taa.pinit.domain.meteo.SimpleMeteo;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {

    public boolean correctMeteo(SimpleMeteo meteo, Activity activity){
        if (meteo.getTemperature() < activity.getTempMin()
                || meteo.getTemperature() > activity.getTempMax())
            return false;

        if (meteo.getSnow() < activity.getSnowMin()
                || meteo.getSnow() > activity.getSnowMax())
            return false;
        if (meteo.getRain() < activity.getRainMin()
                || meteo.getRain() > activity.getRainMax())
            return false;
        if (meteo.getSpeedWind() < activity.getWindSpeedMin()
                || meteo.getSpeedWind() > activity.getWindSpeedMax())
            return false;


        return true;
    }

}
