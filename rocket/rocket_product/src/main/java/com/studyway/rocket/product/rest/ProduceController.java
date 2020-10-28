package com.studyway.rocket.product.rest;

import com.studyway.rocket.product.service.ProducerService;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.client.producer.selector.SelectMessageQueueByHash;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/producer")
public class ProduceController {
    @Resource
    ProducerService producerService;

    @Autowired
    DefaultMQProducer producer;  //这个也是注入进去的

    private String nameSrc = "42.194.196.68:9876";

    private String topicTest = "TOPIC-TEST";

    private String group = "my_group";


    @Resource
    RocketMQTemplate rocketMQTemplate;   //自动注入的


    @RequestMapping("/string")
    public SendResult sendString(@RequestParam String message) {
        System.out.println(producer == rocketMQTemplate.getProducer());  //true
        return producerService.sendString(message);
    }

    @RequestMapping("/string2")
    public SendResult sebdString() throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        System.out.println("producer        " + producer.toString());
        Message message = new Message("TOPIC-TEST", "tag-b", "ceshi".getBytes());

        ArrayList<Message> messages = new ArrayList<>();
        //同步消息
        SendResult send = producer.send(message); //单个消息同送
//        SendResult send2 = producer.send(messages);//多个消息推送
//        //异步消息 不会阻塞消息，
//        producer.send(message, new SendCallback() {
//            @Override
//            public void onSuccess(SendResult sendResult) {
//                //发送成功
//            }
//            @Override
//            public void onException(Throwable e) {
//                //发送失败
//            }
//        });
//        //尝试发送，不关心是否成功 发送的效率最高
//        producer.sendOneway(message);
        return send;
    }


    /**
     * 测试 属性3
     */
    @RequestMapping("/put")
    public SendResult sebdString2() throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        System.out.println("producer        " + producer.toString());
        Message message = new Message("TOPIC-TEST", "tag-b", "ceshi".getBytes());
        message.putUserProperty("age", "18");
        SendResult send = producer.send(message);
        return send;
    }


    /**
     * 测试rocket的事务
     */
    @RequestMapping("/transSend")
    @Transactional
    public SendResult transSend() throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        System.out.println("producer        " + producer.toString());
//        TransactionMQProducer transactionMQProducer = new TransactionMQProducer(group);
//        org.apache.rocketmq.client.exception.MQClientException: The producer group[my_group] has been created before, specify another name please.
//         如果是用以前啓動的時候的group就會報錯，所以需要重新建立個 group 這個的話可能跟 group 一個producer有關
        TransactionMQProducer transactionMQProducer = new TransactionMQProducer("trans_test");
        transactionMQProducer.setNamesrvAddr(nameSrc);
        transactionMQProducer.setTransactionListener(new TransactionListener() {
            @Override
            public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
                System.out.println(msg.getBody().toString());
                System.out.println(arg);
                System.out.println(1 / 0);//这里面如果异常了这个消息就不会发送
                return LocalTransactionState.COMMIT_MESSAGE;
            }

            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt msg) {
                return null;
            }
        });
        transactionMQProducer.start();
        Message message = new Message(topicTest, "tag-b", "ceshi".getBytes());
        TransactionSendResult transactionSendResult = transactionMQProducer.sendMessageInTransaction(message, 1);
        Thread.sleep(5000);
        System.out.println(1 / 0); //这里是不会影响到事务的
        return transactionSendResult;
    }


    /**
     * 测试 顺序
     * 将一定的 消息顺序的放到相同的queue中
     */
    @RequestMapping("/order")
    public SendResult order() throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        System.out.println("producer        " + producer.toString());

        Message message = new Message("TOPIC-TEST", "tag-b", "ceshi".getBytes());

        SendResult send = producer.send(message, new MessageQueueSelector() {
            @Override
            public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                return mqs.get((Integer) arg);
            }
        },1);//arg 可以传输异步的参数，

        //同个queue测试2
        //SendResult send2 = producer.send(message,new SelectMessageQueueByHash(),1);//arg 根据arg的hash值
        return send;
    }


    /**
     * 大批量数据消费
     * @return
     * @throws MQClientException
     * @throws RemotingException
     * @throws InterruptedException
     * @throws MQBrokerException
     */
    @RequestMapping("/order2")
    public SendResult order2() throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        System.out.println("producer        " + producer.toString());

        for (int i = 0; i <1000 ; i++) {
            Message message = new Message("TOPIC-TEST", "tag-b", ("第几个消息？"+i).getBytes());
            SendResult send = producer.send(message, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    System.out.println(mqs.size());
                    return mqs.get((Integer) arg);
                }
            },1);//arg 可以传输异步的参数，
        }


        //同个queue测试2
        //SendResult send2 = producer.send(message,new SelectMessageQueueByHash(),1);//arg 根据arg的hash值
        return null;
    }

}