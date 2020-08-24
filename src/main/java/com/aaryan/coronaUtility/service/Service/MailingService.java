package com.aaryan.coronaUtility.service.Service;

import com.aaryan.coronaUtility.service.Controller.Model.weatherApiModel.ZIP;
import com.aaryan.coronaUtility.service.Domain.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Component
public class MailingService {

    @Autowired
    private Session session;

    @Autowired
    private Environment environment;

    @Autowired
    private userDataJPAImpl userDataJPA;

    @Autowired
    private CoronaVirusDataService coronaVirusDataService;



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


    public boolean sendMail(String to){

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
            return true;

        } catch (MessagingException e) {
            return false;
        }


    }

    public void sendEmailAlerts(String to,ZIP zip){

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(environment.getProperty("server.adminMailSender.email")));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject("Report Test Email");
            message.setText("Dear Customer,"
                    + "\n This is a test mail,please ignore.\n\n"+"here are the user ZIP class contents"
                    + zip);


            Transport.send(message);


        } catch (MessagingException e) {

        }



    }

    @Scheduled(cron = "10 12 * * SUN")
    private  void sendEmailAlerts(){

        List<UserModel> subscribedUsers = userDataJPA.getAllUsers();


        subscribedUsers.stream()
                .forEach(user ->{
                    ZIP currentZIP = this.coronaVirusDataService.weatherRestTemplateCall(Integer.parseInt(user.getPincode()));
                    sendEmailAlerts(user.getEmail(),currentZIP);
                });


    }







}
