package com.aaryan.coronaUtility.service.Service;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
@Component
public class JmsMessageSerivce {

  /*  @Autowired
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

   */
}
