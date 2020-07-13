//package com.example.redis.rpcutil;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
///**
// * Created by zhaolp on 2017/6/30
// * 定时检查监听服务(每10秒监听一次)
// */
//@Component
//public class MqProducerThUtil {
//	private static final Logger logger = LoggerFactory.getLogger(MqProducerThUtil.class);
//
//    @Autowired
//    private MqMsgController defaultMqMsgController;
//
//    @Scheduled(initialDelayString="${jobs.initialDelay}",fixedDelayString="${jobs.fixedDelay}")
//    public void scheduledCheckListener(){
//    	String cmd = defaultMqMsgController.getCMD();
//    	if ("STOP".equals(cmd)) {
//    		logger.debug("检测到停止启动监听信号");
//    		return; //防止停止监听期间又起来监听
//    	}
//
//        defaultMqMsgController.start();
//    }
//}
