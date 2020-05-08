package com.example.redis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@MapperScan("com.example.redis.controller.dao")
@ImportResource(locations = "classpath:application-context.xml")
public class RedisControllerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedisControllerApplication.class, args);
	}

}