package com.aaryan.coronaUtility.service.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.UUID;

@Service
public class MailingService {


    @Autowired
    private Session session;


    @Autowired
    private Environment environment;

   /* public String sendMail(String to){
        String token=UUID.randomUUID().toString();

        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setSubject("Test Mail");
        mailMessage.setText("Sample check mail. authentication checker,enter the given uuid in wesite system:: "+token);
        mailSender.send(mailMessage);
        return token;
    }
    */


    public String sendMail(String to){

        String token= UUID.randomUUID().toString();

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(environment.getProperty("server.adminMailSender.email")));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject("Confirmation Email");
            message.setText("Dear Customer,"
                    + "\n\n Please use the given token to verify this is your email please!"
                    +"\n\n"+token);

            
            Transport.send(message);

            System.out.println("Done");
            return token;

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }


    }




}
