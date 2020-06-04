package com.example.redis.controller.tran.impl;

import com.example.redis.controller.controller.OrderController;
import com.example.redis.controller.dao.MyorderDao;
import com.example.redis.controller.service.OrderService;
import com.example.redis.controller.tran.TransService;
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
public class TransServiceImpl implements TransService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(TransServiceImpl.class);

    @Autowired
    OrderService orderService;
    @Autowired
    AccountService accountService;
    @Autowired
    MyorderDao myorderDao;


    @Override
    public void transCeshi1() {
//        System.out.println(1/a);
        myorderDao.insert(new MyOrder(111, "qq", null, null, new Date(), null, null, null, null));
        log.debug("-------------------------------------------------");
        System.out.println("==================");
        this.transCeshi2();
        Account account = new Account(1213, "etst", 24d, new Date(), "test", new Date(), "test", "ceshi");
        accountService.createAccountFlow(account);
    }

    @Override
    @Transactional
    public void transCeshi2() {

        myorderDao.insert(new MyOrder(1221, "qq", "222", null, new Date(), "22222222", null, null, null));
        System.out.println(1 / 0);


    }
}
