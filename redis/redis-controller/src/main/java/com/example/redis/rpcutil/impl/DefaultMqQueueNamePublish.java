//package com.example.redis.rpcutil.impl;
//
//import com.example.redis.rpcutil.MqQueueNamePublish;
//import com.example.redis.rpcutil.util.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.core.Exchange;
//import org.springframework.amqp.core.FanoutExchange;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.core.MessageProperties;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitAdmin;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.Resource;
//
//
//
///**
// * Created by zhaolp on 2017/7/17 0017.
// */
//@Service
//public class DefaultMqQueueNamePublish implements MqQueueNamePublish {
//    private static final Logger logger = LoggerFactory.getLogger(DefaultMqQueueNamePublish.class);
//
//    private static DefaultMqQueueNamePublish defaultMqQueueNamePublish;
//
//    private CachingConnectionFactory rabbitConnectionFactory;
//
//    @Resource
//    private DefaultMqMsgController mqMsgController;
//
//    private RabbitAdmin rabbitAdmin;
//    public static final String FANOUT_EXCHANGE_NAME="QUEUE_NAME_PUBLISH_FANOUT";
//
//    @Override
//    public void publishAddNewQueue(String queueName) {
//        if(!StringUtils.isEmpty(queueName)){
//            Exchange exchange=new FanoutExchange(FANOUT_EXCHANGE_NAME);
//            rabbitAdmin.declareExchange(exchange);
//            rabbitAdmin.getRabbitTemplate().send(FANOUT_EXCHANGE_NAME,"", new Message(queueName.getBytes(), new MessageProperties()));
//            logger.info("### publish exchange [QUEUE_NAME_PUBLISH_FANOUT] msg ["+queueName+"]");
//        }
//    }
//
//    public void publishStartAllListeners() {
//    	Exchange exchange=new FanoutExchange(FANOUT_EXCHANGE_NAME);
//        rabbitAdmin.declareExchange(exchange);
//        rabbitAdmin.getRabbitTemplate().send(FANOUT_EXCHANGE_NAME,"", new Message("START".getBytes(), new MessageProperties()));
//        logger.info("### publish exchange [QUEUE_NAME_PUBLISH_FANOUT] msg START");
//    }
//
//    public void publishStopAllListeners() {
//    	Exchange exchange=new FanoutExchange(FANOUT_EXCHANGE_NAME);
//        rabbitAdmin.declareExchange(exchange);
//        rabbitAdmin.getRabbitTemplate().send(FANOUT_EXCHANGE_NAME,"", new Message("STOP".getBytes(), new MessageProperties()));
//        logger.info("### publish exchange [QUEUE_NAME_PUBLISH_FANOUT] msg STOP");
//    }
//
//    public synchronized static DefaultMqQueueNamePublish getInstance(){
//        if(defaultMqQueueNamePublish==null){
//            defaultMqQueueNamePublish = new DefaultMqQueueNamePublish();
//        }
//        return defaultMqQueueNamePublish;
//    }
//
//    private DefaultMqQueueNamePublish(){
//    }
//
//    @PostConstruct
//    public void afterPropertiesSet() {
//    	if (rabbitConnectionFactory == null) {
//        	rabbitConnectionFactory = mqMsgController.initRabbitConnectionFactory();
//        }
//        // 初始化AmqpAdmin
//        rabbitAdmin = new RabbitAdmin(rabbitConnectionFactory);
//    }
//
//    public CachingConnectionFactory getConnectionFactory(){
//        return rabbitConnectionFactory;
//    }
//}
