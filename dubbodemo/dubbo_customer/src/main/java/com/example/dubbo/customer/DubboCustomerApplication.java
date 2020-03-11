package com.example.dubbo.customer;

import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
public class DubboCustomerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DubboCustomerApplication.class, args);
	}

}
