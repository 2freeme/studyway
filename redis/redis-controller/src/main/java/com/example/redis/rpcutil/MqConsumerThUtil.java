//package com.example.redis.rpcutil;
//
//import com.example.redis.rpcutil.impl.DefaultMqQueueNameSubscribe;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
///**
// * Created by zhaolp on 2017/6/30 0030.
// * 定时检测是否有新队列需要重新监听（每5秒监听一次）
// */
//@Component
//public class MqConsumerThUtil {
//    @Autowired
//    private DefaultMqQueueNameSubscribe defaultMqQueueNameSubscribe;
//
//    @Scheduled(initialDelayString="${jobs.initialDelay}",fixedDelayString="${jobs.fixedDelaySubscribe}")
//    public void scheduledCheckSubscribe(){
//        defaultMqQueueNameSubscribe.startReceive();
//    }
//}
