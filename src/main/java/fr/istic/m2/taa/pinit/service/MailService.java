package fr.istic.m2.taa.pinit.service;

import fr.istic.m2.taa.pinit.domain.Activity;
import fr.istic.m2.taa.pinit.domain.InscriptionActivity;
import fr.istic.m2.taa.pinit.domain.User;
import fr.istic.m2.taa.pinit.domain.meteo.SimpleMeteo;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class MailService {


    public void sendEmailForActivityWhenBadMeteo(InscriptionActivity inscriptionActivity, SimpleMeteo meteo) throws MessagingException {
        String nameActivity = inscriptionActivity.getActivity().getNameActivity();

        String emailContent = "Bonjour, vous vous ête inscrit à l'activité "+nameActivity+
                "\n La météo de ce week-end est idéal pour ce genre d'activité."+
                "\n Nous vous conseillons donc de pratiqué cette activité"+
                "\n\nTempérature prévue :"+meteo.getTemperature()+" °C"+
                "\nPrécipitation prévue :"+meteo.getRain()+" mm sur 3h en moyenne"+
                "\nNeige prévue :"+meteo.getSnow()+" mm"+
                "\nVitesse du vend :"+meteo.getSpeedWind()+" km/h";


        String objectEmail = "PinIt - inscription à l'activité "+nameActivity;
        sendEmail(inscriptionActivity.getUser().getEmail(),objectEmail,emailContent);
    }

    public void sendEmailForActivityWhenGoodMeteo(InscriptionActivity inscriptionActivity, SimpleMeteo meteo) throws MessagingException {
        String nameActivity = inscriptionActivity.getActivity().getNameActivity();

        String emailContent = "Bonjour, vous vous ête inscrit à l'activité "+nameActivity+
                "\n Cependant, la météo de ce week-end n'est pas idéal pour ce genre d'activité."+
                "\n Nous vous déconseillons donc de ne pas pratiqué cette activité."+
                "\n\nTempérature prévue :"+meteo.getTemperature()+" °C"+
                "\nPrécipitation prévue :"+meteo.getRain()+" mm sur 3h en moyenne"+
                "\nNeige prévue :"+meteo.getSnow()+" mm"+
                "\nVitesse du vend :"+meteo.getSpeedWind()+" km/h";

        String objectEmail = "PinIt - inscription à l'activité "+nameActivity;
        sendEmail(inscriptionActivity.getUser().getEmail(),objectEmail,emailContent);
    }

    private void sendEmail(String to, String object, String text) throws MessagingException {
        String from = "pinitappli@gmail.com";
        String password = "pinitmdp";

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from,password);
                    }
                });
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(from));
        message.setRecipients(Message.RecipientType.TO,
            InternetAddress.parse(to));
        message.setSubject(object);
        message.setText(text);
        Transport.send(message);

    }
}
