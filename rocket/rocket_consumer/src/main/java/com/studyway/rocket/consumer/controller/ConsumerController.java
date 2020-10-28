package com.studyway.rocket.consumer.controller;

import com.studyway.rocket.consumer.service.MQconsumer;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020/10/25 15:11
 */
@RestController
@RequestMapping("/consumer")
public class ConsumerController {

    @RequestMapping("/string")
    public void sendString() throws MQClientException {
      new MQconsumer().customerMQ2();
    }

    /**
     * 测试属性的过滤
     * @throws MQClientException
     */
    @RequestMapping("/put")
    public void put() throws MQClientException {
      new MQconsumer().customerMQ3();
    }

    /**
     * 顺序消费
     * @throws MQClientException
     */
    @RequestMapping("/order")
    public void order() throws MQClientException {
        new MQconsumer().order();
    }

}
