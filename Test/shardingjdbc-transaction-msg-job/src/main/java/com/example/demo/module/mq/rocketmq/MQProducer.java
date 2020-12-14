package com.example.demo.module.mq.rocketmq;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MQProducer
{
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    public void sendMsg(String topic, String msg)
    {
        log.info("发送报文：" + msg);
        this.rocketMQTemplate.convertAndSend(topic, msg);
    } 
}