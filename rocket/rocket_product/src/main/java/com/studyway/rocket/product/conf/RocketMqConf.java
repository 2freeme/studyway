/*
package com.studyway.rocket.product.conf;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

*/
/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020/10/24 23:49
 *//*

@Configuration
@Slf4j
public class RocketMqConf {

    private DefaultMQProducer defaultMQProducer;
    @Value("42.194.196.68:9876")
    private String namesrvAddr;

    @Value("test1")
    private String produceGroup;

    @Bean
    DefaultMQProducer create() throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer(produceGroup);
        producer.setNamesrvAddr("42.194.196.68:9876");
        producer.start();
        log.debug("测试 mq的服务已经开始启动了");
        return producer;
    }

}
*/
