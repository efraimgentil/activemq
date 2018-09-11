package me.efraimgentil.activemq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivemqApplicationTests {

	@Autowired
	private JmsTemplate sender;

	@Test
	public void contextLoads() {
		sender.setDeliveryPersistent(true);
		//sender.
		sender.convertAndSend("testQueue" , "Test message");
	}

}
