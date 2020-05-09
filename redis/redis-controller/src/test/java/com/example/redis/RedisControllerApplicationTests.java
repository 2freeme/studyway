package com.example.redis;

import com.example.redis.controller.service.OrderService;
import com.example.redis.mdredis.JedisUtil;
import com.studyway.redis.test.entity.MyOrder;
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

	@Autowired
	JedisUtil jedisUtil;

	@Test
	public void contextLoads() {
		MyOrder myOrder = new MyOrder();
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

	@Test
	public void testJedis(){
		try {
			Boolean dingpf = jedisUtil.execExistsFromCache("dingpf");
			System.out.println(dingpf);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public  void  test1(){

	}

}
