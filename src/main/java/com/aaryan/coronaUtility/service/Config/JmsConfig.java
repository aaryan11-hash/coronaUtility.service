package com.aaryan.coronaUtility.service.Config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

@Configuration
@RequiredArgsConstructor
@EnableJms
public class JmsConfig {

  /*  @Autowired
    private Environment environment;

    public static final String CORONA_MAIL_MSG="mail-send-stimulus";

    @Bean
    public ConnectionFactory connectionFactory(){
        ActiveMQConnectionFactory connectionFactory=new ActiveMQConnectionFactory();
        connectionFactory.setBrokerURL(environment.getProperty("activemq.broker-url"));
        connectionFactory.setUserName(environment.getProperty("activemq.username"));
        connectionFactory.setPassword(environment.getProperty("activemq.password"));
        connectionFactory.setTrustAllPackages(true);

        return connectionFactory;
    }

    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerFactory(@Qualifier("connectionFactory") ActiveMQConnectionFactory connectionFactory
            ,QueueHandler queueHandler){
        SimpleJmsListenerContainerFactory containerFactory=new SimpleJmsListenerContainerFactory();
        containerFactory.setConnectionFactory(connectionFactory);
        containerFactory.setErrorHandler(queueHandler);

        return containerFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate(){

        return new JmsTemplate(connectionFactory());
    }

   @Bean
    public ObjectMapper objectMapper(){

        return new ObjectMapper();
   }

*/



}
