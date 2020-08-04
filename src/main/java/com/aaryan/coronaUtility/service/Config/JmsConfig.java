package com.aaryan.coronaUtility.service.Config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.config.SimpleJmsListenerContainerFactory;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;

import javax.jms.ConnectionFactory;

@Configuration
@RequiredArgsConstructor
@EnableJms
public class JmsConfig {

    @Autowired
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





}
