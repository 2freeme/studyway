package com.studyway.rocket.product.rest;

import com.studyway.rocket.product.service.ProducerService;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;

@RestController
@RequestMapping("/producer")
public class ProduceController {
    @Resource
    ProducerService producerService;

    @Autowired
    DefaultMQProducer producer;  //这个也是注入进去的


    @Resource
    RocketMQTemplate rocketMQTemplate;   //自动注入的


    @RequestMapping("/string")
    public SendResult sendString(@RequestParam String message) {
        System.out.println(producer == rocketMQTemplate.getProducer());  //true
        return producerService.sendString(message);
    }

    @RequestMapping("/string2")
    public SendResult sebdString() throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        System.out.println("producer        " + producer.toString());
        Message message = new Message("TOPIC-TEST","tag-b", "ceshi".getBytes());

        ArrayList<Message> messages = new ArrayList<>();
        //同步消息
        SendResult send = producer.send(message); //单个消息同送
//        SendResult send2 = producer.send(messages);//多个消息推送
//        //异步消息 不会阻塞消息，
//        producer.send(message, new SendCallback() {
//            @Override
//            public void onSuccess(SendResult sendResult) {
//                //发送成功
//            }
//            @Override
//            public void onException(Throwable e) {
//                //发送失败
//            }
//        });
//        //尝试发送，不关心是否成功 发送的效率最高
//        producer.sendOneway(message);
        return send;
    }


    /**
     *测试 属性3
     */
    @RequestMapping("/put")
    public SendResult sebdString2() throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        System.out.println("producer        " + producer.toString());
        Message message = new Message("TOPIC-TEST","tag-b", "ceshi".getBytes());
        message.putUserProperty("age","18");
        SendResult send = producer.send(message);
        return send;
    }


}