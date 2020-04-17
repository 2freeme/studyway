package com.example.redis.demo2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@MapperScan("com.example.redis.demo2.dao")
public class RedisDemo2Application {

	public static void main(String[] args) {
		SpringApplication.run(RedisDemo2Application.class, args);
	}

}
