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
@RabbitListener(queues = "queuetopic4")
public class Comsumer6 {
    @RabbitHandler
    private  void test1(String message){
        System.out.println("queuetopic4 : "+ message);
    }
}
