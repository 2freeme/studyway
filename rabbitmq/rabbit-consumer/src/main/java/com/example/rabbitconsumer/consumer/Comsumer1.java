package com.example.rabbitconsumer.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2019/10/7 17:50
 */
@Component
@RabbitListener(queues = "queuefauout1")
public class Comsumer1 {
    @RabbitHandler
    private  void test1(String message){
        System.out.println("comsumer1 : "+ message);
    }
}
