package com.studyway.rocket.consumer.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import javax.jws.WebMethod;
import java.util.List;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020/10/25 10:47
 */
@Slf4j
@Service
public   class MQconsumer {
  // @Autowired
  //  DefaultMQPushConsumer defaultMQPushConsumer;


    //public  void consumerMq() throws MQClientException {
        /*每一个consumer只能监听一个consumer*/
        /*第二个参数指的是过滤器*/

//        defaultMQPushConsumer.subscribe("string-topic","*");
//        defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently() {
//            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
//                for (MessageExt msg : msgs) {
//                    System.out.println(msg.getBody());
//                }
//                return  ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//            }
//        });
//        defaultMQPushConsumer.start();
//    }

    public void customerMQ2() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("my_group");
        consumer.setNamesrvAddr("42.194.196.68:9876");
        consumer .subscribe("TOPIC-TEST","tag-d");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt msg : msgs) {
                    System.out.println(msg);
                }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }


        });
        consumer.start();


    }

}
