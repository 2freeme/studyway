package com.example.redis;

import com.example.redis.controller.service.OrderService;
import com.studyway.redis.test.entity.Myorder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("com.example.redis.controller.dao")
public  class RedisControllerApplicationTests {
	@Autowired
	OrderService orderService;
	@Autowired
	RedisTemplate redisTemplate;

	@Test
	public void contextLoads() {
		Myorder myOrder = new Myorder();
		myOrder.setItemName("手机");
		myOrder.setQty(1);
		myOrder.setUserName("dingpf1");
		orderService.submitOrder(myOrder);

	}
	@Test
	public void set(){
		redisTemplate.opsForValue().set("myKey","myValue");
		System.out.println("测试" +redisTemplate.opsForValue().get("myKey"));
	}

}
