package fr.istic.m2.taa.pinit.config;

import fr.istic.m2.taa.pinit.domain.InscriptionActivity;
import fr.istic.m2.taa.pinit.domain.meteo.SimpleMeteo;
import fr.istic.m2.taa.pinit.repository.InscriptionActivityRepository;
import fr.istic.m2.taa.pinit.service.ActivityService;
import fr.istic.m2.taa.pinit.service.MailService;
import fr.istic.m2.taa.pinit.service.MeteoService;
import fr.istic.m2.taa.pinit.service.exception.BadDayForWeekEndMeteo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class ScheduledTasks {

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private MailService mailService;

    private MeteoService meteoService;

    private ActivityService activityService;

    private InscriptionActivityRepository inscriptionActivityRepository;

    public ScheduledTasks(MailService mailService, MeteoService meteoService, ActivityService activityService, InscriptionActivityRepository inscriptionActivityRepository) {
        this.mailService = mailService;
        this.meteoService = meteoService;
        this.activityService = activityService;
        this.inscriptionActivityRepository = inscriptionActivityRepository;
    }

    //@Scheduled(fixedRate = 10000)
    @Scheduled(cron = "0 0 18 * * FRI")//tous les jeudi à 18h
    public void reportCurrentTime() {

        List<InscriptionActivity> inscriptionActivities = inscriptionActivityRepository.findAll();

        for (InscriptionActivity inscriptionActivity : inscriptionActivities) {
            log.debug("Envois d'un email en cours ...");
            double latitute = inscriptionActivity.getLocalisation().getLatitude();
            double longitude = inscriptionActivity.getLocalisation().getLongitude();

            SimpleMeteo averageMeteo = null;
            try {
                averageMeteo = meteoService.getAverageMeteoOfNextWeekEnd(latitute,longitude);
            } catch (BadDayForWeekEndMeteo badDayForWeekEndMeteo) {
                badDayForWeekEndMeteo.printStackTrace();
            }


            boolean goodMeteo = activityService.correctMeteo(averageMeteo, inscriptionActivity.getActivity());
            try {
                if (goodMeteo){
                    mailService.sendEmailForActivityWhenGoodMeteo(inscriptionActivity,averageMeteo);
                }else{
                    mailService.sendEmailForActivityWhenBadMeteo(inscriptionActivity,averageMeteo);
                }
            } catch (MessagingException e) {
                log.error("Problem when try to send email");
                e.printStackTrace();
            }
        }

    }
}