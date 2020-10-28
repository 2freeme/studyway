package com.studyway.rocket.consumer.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020/10/25 10:47
 */
@Slf4j
@Service
public class MQconsumer {
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
        consumer.subscribe("TOPIC-TEST", "tag-b");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt msg : msgs) {
                    System.out.println(msg);
                    // throw new RuntimeException();
                }
                System.out.println("ceshi ");
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                //return null;
            }


        });
        consumer.start();
    }


    //测试tag属性
    public void customerMQ3() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("my_group");
        consumer.setNamesrvAddr("42.194.196.68:9876");
        MessageSelector messageSelector = MessageSelector.bySql("age>17");
        consumer.subscribe("TOPIC-TEST", messageSelector);
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt msg : msgs) {
                    System.out.println(msg);
                    // throw new RuntimeException();
                }
                System.out.println("ceshi ");
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                //return null;
            }


        });
        consumer.start();
    }


    //测试顺序消费属性
    public void order() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("my_group");
        consumer.setNamesrvAddr("42.194.196.68:9876");
        //MessageSelector messageSelector = MessageSelector.bySql("age>17");
        consumer.subscribe("TOPIC-TEST", "*");

        consumer.setConsumeThreadMin(10);//最小线程
        consumer.setConsumeThreadMax(20);//最大线程
        //顺序消费
        consumer.registerMessageListener(new MessageListenerOrderly() {
            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                for (MessageExt msg : msgs) {
                    System.out.println(Thread.currentThread().getName()+"   :   "+ new String(msg.getBody()));
                }
                System.out.println("=================");

                return ConsumeOrderlyStatus.SUCCESS;
            }
        });
        consumer.start();
    }

}
