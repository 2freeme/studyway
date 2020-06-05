package com.example.redis.controller.tran.impl;

import com.example.redis.controller.dao.MyorderDao;
import com.example.redis.controller.service.OrderService;
import com.example.redis.controller.tran.TransService;
import com.example.redis.controller.tran.TransService2;
import com.studyway.redis.test.entity.Account;
import com.studyway.redis.test.entity.MyOrder;
import com.studyway.redis.test.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @Author： Dingpengfei
 * @Description： 用作测试事务
 * @Date： 2020/6/4 23:20
 */
@Service
public class TransService2Impl implements TransService2 {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TransService2Impl.class);

    @Autowired
    OrderService orderService;
    @Autowired
    AccountService accountService;
    @Autowired
    MyorderDao myorderDao;


    @Override
    @Transactional
    public void ceshi1() {
        System.out.println(1/0);
    }
}
