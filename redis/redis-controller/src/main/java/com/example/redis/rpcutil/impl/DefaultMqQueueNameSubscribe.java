//package com.example.redis.rpcutil.impl;
//
//import com.example.redis.rpcutil.MqMsgController;
//import com.example.redis.rpcutil.util.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.core.*;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitAdmin;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.Resource;
//import java.util.HashMap;
//
///**
// * Created by zhaolp on 2017/7/17 0017.
// */
//@Service
//public class DefaultMqQueueNameSubscribe {
//    private static final Logger logger = LoggerFactory.getLogger(DefaultMqQueueNameSubscribe.class);
//    private CachingConnectionFactory rabbitConnectionFactory;
//
//    private RabbitAdmin rabbitAdmin;
//    private static DefaultMqQueueNameSubscribe defaultMqQueueNameSubscribe;
//    @Resource
//    private MqMsgController defaultMqMsgController;
//
//    public static final String FANOUT_EXCHANGE_NAME="QUEUE_NAME_PUBLISH_FANOUT";
//    public static final String FANOUT_QUEUE_NAME="QUEUE_NAME_PUBLISH_FANOUT";
//
//    public synchronized static DefaultMqQueueNameSubscribe getInstance(){
//        if(defaultMqQueueNameSubscribe==null){
//            defaultMqQueueNameSubscribe = new DefaultMqQueueNameSubscribe();
//        }
//        return defaultMqQueueNameSubscribe;
//    }
//
//    private DefaultMqQueueNameSubscribe(){
//    }
//
//    @PostConstruct
//    public void afterPropertiesSet() {
//    	if (rabbitConnectionFactory == null) {
//        	rabbitConnectionFactory = defaultMqMsgController.initRabbitConnectionFactory();
//        }
//
//        // 初始化AmqpAdmin
//        rabbitAdmin = new RabbitAdmin(rabbitConnectionFactory);
//        //开始监听服务
//        Exchange exchange=new FanoutExchange(FANOUT_EXCHANGE_NAME);
//        rabbitAdmin.declareExchange(exchange);
//        Queue fanout=new Queue(FANOUT_QUEUE_NAME);
//        rabbitAdmin.declareQueue(fanout);
//        Binding bind=new Binding(FANOUT_QUEUE_NAME, Binding.DestinationType.QUEUE, FANOUT_EXCHANGE_NAME, FANOUT_EXCHANGE_NAME, new HashMap<String, Object>());
//        rabbitAdmin.declareBinding(bind);
//        logger.info(" [*] Waiting for messages. To exit press CTRL+C");
//    }
//
//    public CachingConnectionFactory getConnectionFactory(){
//        return rabbitConnectionFactory;
//    }
//
//    public void startReceive(){
//        if (!StringUtils.isEmpty(FANOUT_QUEUE_NAME)){
//            Message msg=rabbitAdmin.getRabbitTemplate().receive(FANOUT_QUEUE_NAME);
//            if(msg!=null){
//                String message = new String(msg.getBody());
//                if (!StringUtils.isEmpty(message)) {
//                	logger.info(" [x] Received exchange [QUEUE_NAME_PUBLISH_FANOUT] msg -> '" + message + "'");
//
//                	if (!StringUtils.isEmpty(message) && message.equals("START")) {
//                		defaultMqMsgController.setCMD("START");
//                		defaultMqMsgController.start();
//                    } else if (!StringUtils.isEmpty(message) && message.equals("STOP")) {
//                    	defaultMqMsgController.setCMD("STOP");
//                    	defaultMqMsgController.stop();
//                    } else if(!StringUtils.isEmpty(message) && !message.startsWith("amq.")){
//                    	defaultMqMsgController.start(message);
//                    }
//                }
//
//            } else {
//                //logger.debug(" [*] No messages receive,continue to wait.");
//            }
//        }
//    }
//}
