package me.efraimgentil.activemq.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jca.cci.connection.CciLocalTransactionManager;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.jms.Session;

@Configuration
@EnableTransactionManagement
@EnableJms
public class JmsConfig {

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory(){
        return new ActiveMQConnectionFactory("tcp://localhost:61616");
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(CachingConnectionFactory ccf, PlatformTransactionManager platformTransactionManager) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(ccf);
        factory.setTransactionManager(platformTransactionManager);
        factory.setSessionTransacted(true);
        factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        factory.setConcurrency("3-10");
        return factory;
    }

    @Bean
    public CachingConnectionFactory cachingConnectionFactory() {
        CachingConnectionFactory ccf = new CachingConnectionFactory(activeMQConnectionFactory());
        return ccf;
    }

    @Bean
    public JmsTemplate jmsTemplate(CachingConnectionFactory ccf) {
        JmsTemplate jmsTemplate = new JmsTemplate(ccf);
        jmsTemplate.setSessionTransacted(true);
        return jmsTemplate;
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager(CachingConnectionFactory ccf){
        return new JmsTransactionManager(ccf);
    }

    public void t(){
        GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
    }

}
