package com.studyway.rocket.product.rest;

import com.studyway.rocket.product.service.ProducerService;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/producer")
public class ProduceController {
    @Resource
    ProducerService producerService;

    @RequestMapping("/string")
    public SendResult sendString(@RequestParam  String message){
        return producerService.sendString(message);
    }

    @RequestMapping("/string2")
    public void sebdString() throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("test1");
        producer.setNamesrvAddr("42.194.196.68:9876");
        producer.start();
        producer.send(new Message("topic1","ceshi".getBytes()));

    }


//    @PostMapping("/user")
//    public SendResult sendUser(@RequestBody  user){
//        return producerService.sendUser(user);
//    }
}