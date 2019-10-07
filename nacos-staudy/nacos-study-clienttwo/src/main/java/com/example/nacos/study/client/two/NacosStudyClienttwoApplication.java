package com.example.nacos.study.client.two;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class NacosStudyClienttwoApplication {
	@LoadBalanced
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	public static void main(String[] args) {
		SpringApplication.run(NacosStudyClienttwoApplication.class, args);
	}

	@RequestMapping("/nacos/{str}")
	public  String nacosTest(@PathVariable String str){
		RestTemplate restTemplate = restTemplate();
		return restTemplate.getForObject("http://localhost:8070/echo/" + str, String.class);
		//return restTemplate.getForObject("http://nacos-study-client/echo/" + str, String.class);

	}

}
