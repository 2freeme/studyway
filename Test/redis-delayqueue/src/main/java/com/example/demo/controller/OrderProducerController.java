package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.model.Order;
import com.example.demo.service.RefundOrderServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/test")
@Api(tags = "生产者", description = "OrderProducerController")
public class OrderProducerController {
	  private static final Logger logger = LoggerFactory.getLogger(OrderProducerController.class);
    @Autowired
    private RedissonClient redisson;
    
    /**
     * http://127.0.0.1:8888/test/producer
     */
    @GetMapping("/producer")
    @ApiOperation(value = "生产")
    public void Producer() {

        RBlockingDeque<String> blockingDeque = redisson.getBlockingDeque("jiuyang_order_message"); //自动完成订单
        RDelayedQueue<String> delayedQueue = redisson.getDelayedQueue(blockingDeque);
        for (long i = 0; i < 5; i++) {
            try {
                Order user = new Order();
                user.setId(i);
                user.setNamme("123");
                delayedQueue.offer(JSON.toJSONString(user), 10+i*5, TimeUnit.SECONDS);  //秒 这里可以换单位 小时  分钟 天
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                System.out.println("发送条数  " + i+"  参数为   "+user.toString()+"   时间    "+sdf.format(new Date()));
            } catch (Exception e) {
            	logger.error("Producer error!", e);
            }
        }


    }
}
