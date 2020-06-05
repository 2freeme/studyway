package com.example.redis.controller.service.impl;

import com.example.redis.controller.dao.MyorderDao;
import com.example.redis.controller.service.OrderService;
import com.example.redis.controller.service.RedisTestService;
import com.example.redis.controller.tran.TransService;
import com.studyway.redis.test.entity.MyOrder;
import com.studyway.redis.test.entity.RedisDemo;
import com.studyway.redis.test.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author： Dingpengfei
 * @Description：用来测试redis的回滚
 * @Date： 2020-6-5 11:09
 */
@Service
public class RedisTestServiceImpl implements RedisTestService {
    @Autowired
    OrderService orderService;
    @Autowired
    AccountService accountService;
    @Autowired
    MyorderDao myorderDao;
    @Autowired
    TransService transService;

    @Override
    public void testRedisRollBack() {
        MyOrder MyOrder = new MyOrder();
        MyOrder.setItemName("手机");
        MyOrder.setQty(1);
        MyOrder.setUserName("dingpf1");
        RedisDemo redisDemo = new RedisDemo("dingpf", "11111");
        System.out.println();
        // redisLock.setRedis(redisDemo);
         orderService.submitOrder(MyOrder);
    }
}
