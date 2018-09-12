package me.efraimgentil.activemq.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.HashMap;
import java.util.Map;

@Component
public class EventListener {


    private Map<String, Integer> messages = new HashMap<>();

    @Autowired
    private JmsTemplate sender;

    @Transactional(rollbackFor = RuntimeException.class)
    @JmsListener(destination = "testQueue")
    public void receive(String messageString , Session session , Message message) throws JMSException {

        System.out.println(String.format("%s : %s" , "isRedelivery" , message.getJMSRedelivered()));
        System.out.println(String.format("%s : %s" , "deliveryCount" , message.getIntProperty("JMSXDeliveryCount")));


        if(!messages.containsKey(messageString)){
            messages.put(messageString , 1);
            System.out.println(message.getJMSDeliveryMode());
//            System.out.println(message.getJMSDeliveryTime());
            System.out.println(message.getJMSDestination());
            System.out.println(message.getJMSMessageID());

            sender.convertAndSend("test1" , "Hi Test 1");
            throw new RuntimeException("MEEEE");

        }
        System.out.println(messageString);

        sender.convertAndSend("test1" , "Hi Test 1");
        sender.convertAndSend("test2" , "Hi Test 2");
    }

    @JmsListener(destination = "test1")
    public void receiveTest1(String messageString , Session session , Message message) throws JMSException {

        System.out.println(messageString);

    }

    @JmsListener(destination = "test2")
    public void receiveTest2(String messageString , Session session , Message message) throws JMSException {

        System.out.println(messageString);

    }

}
