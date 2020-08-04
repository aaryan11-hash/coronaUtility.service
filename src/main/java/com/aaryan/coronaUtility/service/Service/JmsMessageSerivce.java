package com.aaryan.coronaUtility.service.Service;

import com.aaryan.coronaUtility.service.Config.JmsConfig;
import com.aaryan.coronaUtility.service.Controller.Model.UserModelDto;
import com.aaryan.coronaUtility.service.Domain.UserModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@Component
public class JmsMessageSerivce {

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    private MailingService mailingService;

    @Autowired
    private ObjectMapper objectMapper;



   @JmsListener(destination = JmsConfig.CORONA_MAIL_MSG,containerFactory = "jmsListenerContainerFactory")
   public void sendEmailToNewUser(@Payload String usermodel){


       UserModelDto userModel= null;
       try {
           userModel = objectMapper.readValue(usermodel, UserModelDto.class);
       } catch (JsonProcessingException e) {
           e.printStackTrace();
       }

       System.out.println("received message");
       System.out.println(userModel);
       mailingService.sendMail(userModel.getEmail());


    }
}
