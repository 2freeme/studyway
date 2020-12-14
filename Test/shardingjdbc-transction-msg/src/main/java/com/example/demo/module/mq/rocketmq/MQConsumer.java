package com.example.demo.module.mq.rocketmq;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RocketMQMessageListener(topic = "${rocketmq.producer.topic}", consumerGroup = "${rocketmq.producer.group}")
public class MQConsumer implements RocketMQListener<String>
{
    @Override
    public void onMessage(String msg)
    {
        log.info("收到消息:" + msg);
    }
}

