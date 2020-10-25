package com.studyway.rocket.product.service.impl;

import com.studyway.rocket.product.api.Student;
import com.studyway.rocket.product.service.ProducerService;
import lombok.extern.log4j.Log4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020/10/11 11:18
 */
@Service
@Log4j
public class ProducerServiceImpl implements ProducerService {

    public Logger logger = LoggerFactory.getLogger(ProducerServiceImpl.class);

    @Resource
    RocketMQTemplate rocketMQTemplate;   //自动注入的

    private String springTopic = "string-topic";

    private String userTopic = "user-topic";


      public SendResult sendString(String s) {
        SendResult sendResult = rocketMQTemplate.syncSend(springTopic, s);
        logger.debug("测试  topic {} s {} sendResult {}", springTopic, s ,sendResult);
          System.out.println("rocketMQTemplate           "  +rocketMQTemplate.getProducer().toString());
        return sendResult;

    }

     public SendResult sendStudent(Student student) {
        SendResult sendResult = rocketMQTemplate.syncSend(userTopic, student);
        logger.debug("测试  topic {} student {} sendResult {}", springTopic, student ,sendResult);
        return sendResult;
    }

}
