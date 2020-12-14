package com.example.demo.module.mq.rocketmq;

import java.util.Map;
import java.util.concurrent.Callable;

import javax.annotation.Resource;

import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.common.transanctionMsg.MessageEntity;
import com.example.demo.common.transanctionMsg.TransactionInterceptorHandler;
import com.example.demo.common.utils.IdGenerator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MQProducer2
{
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    @Resource
    private TransactionInterceptorHandler transactionInterceptorHandler;
    
    
    public MessageEntity sendMessage(MessageEntity entity) {
        if (transactionInterceptorHandler.hasTransaction()) {
            Callable<MessageEntity> callable = () -> doSendMessage(entity);
            transactionInterceptorHandler.addAction(callable);
            transactionInterceptorHandler.addMessage(entity);
            return null;
        }else {
            return doSendMessage(entity);
        }
    }

    private MessageEntity doSendMessage(MessageEntity entity) {
    	log.info("发送topic:{},报文：{}",entity.getTopic(), entity.getContent());
    	try {
    		 this.rocketMQTemplate.convertAndSend(entity.getTopic(), entity.getContent());
    	}catch (Exception e) {
			log.error("doSendMessage error!", e);
			return null;
		}
        return entity;
    }
}

