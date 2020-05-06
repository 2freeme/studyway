package com.example.redis.demo1;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@MapperScan("com.example.redis.demo1.dao")
public class RedisDemo1Application {

	public static void main(String[] args) {
		SpringApplication.run(RedisDemo1Application.class, args);
	}

}
