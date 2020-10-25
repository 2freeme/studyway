package com.studyway.rocket.consumer;

import com.studyway.rocket.consumer.service.MQconsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RocketConsumerApplication {

	public static void main(String[] args) throws MQClientException {
		SpringApplication.run(RocketConsumerApplication.class, args
		);
	}

}
