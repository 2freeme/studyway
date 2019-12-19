package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@EnableEurekaClient
@RestController
@SpringBootApplication
public class EurakeApplication {

	public static void main(String[] args) {
		SpringApplication.run(EurakeApplication.class, args);
	}
	@Value("${server.port}")
	public String port;

	@RequestMapping("/test")
	public String hi(@RequestParam  String name){
		System.out.println("hi,我的port是 ："+port);
		return "你好"+name +"你已经进入了eurekaCelient的方法 端口号：" +port ;
	}
}


