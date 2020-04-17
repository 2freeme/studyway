package com.example.redis.demo1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisDemo1ApplicationTests {

	@Autowired
	private RedisTemplate<String,String> redisTemplate;

	@Test
	public void set(){
		redisTemplate.opsForValue().set("myKey","myValue");
		System.out.println("测试" +redisTemplate.opsForValue().get("myKey"));
	}
}
