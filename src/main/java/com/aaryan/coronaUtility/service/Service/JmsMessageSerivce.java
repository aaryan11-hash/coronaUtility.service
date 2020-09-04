package com.aaryan.coronaUtility.service.Service;

import com.aaryan.coronaUtility.service.Config.JmsConfig;
import com.aaryan.coronaUtility.service.Controller.Mappers.ModelMapper;
import com.aaryan.coronaUtility.service.Controller.Model.UserModelDto;
import com.aaryan.coronaUtility.service.Domain.UserModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
@Component
public class JmsMessageSerivce {

    @Autowired
    JmsTemplate jmsTemplate;

    @Autowired
    private MailingService mailingService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private userDataJPAImpl userDataJPA;

//    @Autowired
//    private ModelMapper userModelMapper;



   @JmsListener(destination = JmsConfig.CORONA_MAIL_MSG,containerFactory = "jmsListenerContainerFactory")
   public void sendEmailToNewUser(@Payload String usermodel, @Headers MessageHeaders headers){


       UserModelDto userModel= null;
       try {
           userModel = objectMapper.readValue(usermodel, UserModelDto.class);
       } catch (JsonProcessingException e) {
           e.printStackTrace();
       }

       System.out.println("received message");
       System.out.println(userModel);
        boolean successfulMail = mailingService.sendMail(userModel.getEmail());
        //UserModel test = userModelMapper.userModelDtoconvertuserModel(userModel);

      // System.out.println(test);

        //if(successfulMail)
            //userDataJPA.saveUserData(userModelMapper.userModelDtoconvertuserModel(userModel));


    }
}
