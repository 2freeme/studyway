package com.example.rabbitproducer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitProducerApplicationTests {
	@Autowired
	RabbitTemplate rabbitTemplate;



	@Test
	/**
	 * 这里是直接模式
	 */
	public void contextLoads() {
		rabbitTemplate.convertAndSend("queueTest1","testOne:dir");
	}

	@Test
	/**
	 * 这里是分列模式 可以分发给多个的queue
	 */
	public  void test2(){
		rabbitTemplate.convertAndSend("exchangeTest1","","exchangeTest1");

	}



	@Test
	/**
	 * 这里是主题模式 可以根据不同的change来进行改变
	 */
	public  void testTopic(){
		rabbitTemplate.convertAndSend("exchangeTopic","rabbit.topic.test","rabbit.topic.test");

	}


	@Test
	/**
	 * 这里是主题模式 可以根据不同的change来进行改变
	 */
	public  void testTopic2(){
		rabbitTemplate.convertAndSend("exchangeTopic","aa.topic.test.bb","aa.topic.test.bb");

	}

	@Test
	/**
	 * 这里是主题模式 可以根据不同的change来进行改变
	 */
	public  void testTopic3(){
		rabbitTemplate.convertAndSend("exchangeTopic","rabbit.test.topic"," rabbit.test.topic");

	}


	@Test
	/**
	 * 这里是主题模式 可以根据不同的change来进行改变
	 */
	public  void testTopic4(){
		rabbitTemplate.convertAndSend("exchangeTopic","rabbit.topic","rabbit.topic");

	}

}
