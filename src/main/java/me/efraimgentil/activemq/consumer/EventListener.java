package me.efraimgentil.activemq.consumer;

import org.springframework.jms.annotation.JmsListener;
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

    @Transactional(rollbackFor = RuntimeException.class)
    @JmsListener(destination = "testQueue" , subscription = "testQueue")
    public void receive(String messageString , Session session , Message message) throws JMSException {


        System.out.println(String.format("%s : %s" , "isRedelivery" , message.getJMSRedelivered()));
        System.out.println(String.format("%s : %s" , "deliveryCount" , message.getIntProperty("JMSXDeliveryCount")));


        if(!messages.containsKey(messageString)){
            messages.put(messageString , 1);
            System.out.println(message.getJMSDeliveryMode());
//            System.out.println(message.getJMSDeliveryTime());
            System.out.println(message.getJMSDestination());
            System.out.println(message.getJMSMessageID());
            session.rollback();
            throw new RuntimeException("MEEEE");

        }
        System.out.println(messageString);

        message.acknowledge();
    }

}
