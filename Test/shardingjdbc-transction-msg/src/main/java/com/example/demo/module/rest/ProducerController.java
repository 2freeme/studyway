package com.example.demo.module.rest;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.module.mq.rocketmq.MQProducer;


/**
 * 生产者
 */
@RestController
public class ProducerController {

    @Autowired
    private MQProducer springProducer;

    /**
     * 同步消息
     * http://127.0.0.1:8888/syncNews
     *
     * @author 小道仙
     * @date 2020年3月3日
     */
    @GetMapping("/syncNews")
    public String syncNews(){
    	springProducer.sendMsg("newTopic", "消息测试");
        return "success";
    }

 

}
