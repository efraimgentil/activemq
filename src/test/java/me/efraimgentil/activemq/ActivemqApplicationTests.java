package me.efraimgentil.activemq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.jms.DeliveryMode;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivemqApplicationTests {

	@Autowired
	private JmsTemplate sender;

	@Test
	public void contextLoads() {
		sender.setExplicitQosEnabled(true);
		sender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		//sender.setDeliveryPersistent(true);
		//sender.
		sender.convertAndSend("testQueue" , "Test message");
	}

	@Test
	public void contextLoads1() {
		sender.setExplicitQosEnabled(true);
		sender.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
		//sender.setDeliveryPersistent(true);
		//sender.
		sender.convertAndSend("testQueue" , "error");
	}

	@Test
	public void contextLoads2() {
		sender.setDeliveryPersistent(true);
		//sender.
		sender.convertAndSend("testQueue2" , "Test message");
	}


}
