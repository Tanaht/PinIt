package fr.istic.m2.taa.pinit.service;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class MailService {
    public static void main(String[] arg) throws Exception{

        String dest = "charp.antoine@gmail.com";

        new MailService().sendEmail(dest, "Comment agrandir la taille de votre pénis","");

    }

    public void sendEmail(String to, String object, String text) throws MessagingException {
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
