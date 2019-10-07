package com.example.rabbitconsumer.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author： Dingpengfei
 * @Description：topic
 * @Date： 2019/10/7 17:50
 */
@Component
@RabbitListener(queues = "queuetopic2")
public class Comsumer4 {
    @RabbitHandler
    private  void test1(String message){
        System.out.println("queuetopic2 : "+ message);
    }
}
