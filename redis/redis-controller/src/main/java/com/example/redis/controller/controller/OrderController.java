package com.example.redis.controller.controller;

import com.example.redis.controller.dao.MyorderDao;
import com.example.redis.lock.reentrant.redislock.RedisLock;
import com.studyway.redis.test.entity.Account;
import com.studyway.redis.test.entity.MyOrder;
import com.studyway.redis.test.entity.RedisDemo;
import com.studyway.redis.test.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-3-24 17:40
 */
@ResponseBody
@RestController
@RequestMapping("/test")
public class OrderController {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(OrderController.class);

   /* @Autowired
    OrderService orderService*/
   @Autowired
    AccountService accountService;
   @Autowired
    MyorderDao myorderDao;


    @Autowired
    RedisLock redisLock;

    @RequestMapping("/test")
    @Transactional
    public String test(){
        MyOrder MyOrder = new MyOrder();
        MyOrder.setItemName("手机");
        MyOrder.setQty(1);
        MyOrder.setUserName("dingpf1");
        RedisDemo redisDemo =new RedisDemo("dingpf","11111");
        System.out.println();
       // redisLock.setRedis(redisDemo);
       // orderService.submitOrder(MyOrder);
        int a = 0;
//        System.out.println(1/a);
        myorderDao.insert(new MyOrder(111,"qq",null,null,null,null,null,null,null));
        log.debug("-------------------------------------------------");
        System.out.println("==================");
        Account account= new Account(1213,"etst",24d,new Date(),"test",new Date(),"test","ceshi");
        accountService.createAccountFlow(account);
        return "success";

    }
}
