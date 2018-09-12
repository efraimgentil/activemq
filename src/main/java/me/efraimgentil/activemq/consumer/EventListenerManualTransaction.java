package me.efraimgentil.activemq.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.jms.*;
import java.util.HashMap;
import java.util.Map;

@Component
public class EventListenerManualTransaction {


    private Map<String, Integer> messages = new HashMap<>();

    @Autowired
    private JmsTemplate sender;

    @JmsListener(destination = "testQueue2" )
    public void receive(String messageString , Session session , Message message) throws JMSException {

        System.out.println(String.format("%s : %s" , "isRedelivery" , message.getJMSRedelivered()));
        System.out.println(String.format("%s : %s" , "deliveryCount" , message.getIntProperty("JMSXDeliveryCount")));
        try {
            if (!messages.containsKey(messageString)) {
                messages.put(messageString, 1);
                System.out.println(message.getJMSDeliveryMode());
                System.out.println(message.getJMSDestination());
                System.out.println(message.getJMSMessageID());

                MessageProducer test1 = session.createProducer(session.createQueue("test1"));
                test1.send(session.createTextMessage("Hi Test 1"));
                throw new RuntimeException("MEEEE");

            }
            System.out.println(messageString);
            System.out.println( session.getTransacted());

            MessageProducer test1 = session.createProducer(session.createQueue("test1"));
            test1.send(session.createTextMessage("Hi Test 1"));
            MessageProducer test2 = session.createProducer(session.createQueue("test2"));
            test2.send(session.createTextMessage("Hi Test 2"));
            session.commit();
        }catch(Exception e){
            System.out.println("Rolling back");
            session.rollback();
        }
        //session.commit();
    }

}
