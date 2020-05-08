package com.example.redis.controller.controller;

import com.example.redis.controller.rediservie.RedisService;
import com.studyway.redis.test.entity.MyOrder;
import com.studyway.redis.test.entity.RedisDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-3-24 17:40
 */
@ResponseBody
@RestController
@RequestMapping("/test")
public class OrderController {

   /* @Autowired
    OrderService orderService*/ ;


    @Autowired
    RedisService redisService;

    @RequestMapping("/test")
    @Transactional
    public String test(){
        MyOrder MyOrder = new MyOrder();
        MyOrder.setItemName("手机");
        MyOrder.setQty(1);
        MyOrder.setUserName("dingpf1");
        RedisDemo redisDemo =new RedisDemo("dingpf","11111");
        System.out.println();
        redisService.setRedis(redisDemo);
       // orderService.submitOrder(MyOrder);
        int a = 0;
        System.out.println(1/a);
        System.out.println("==================");
        return "success";
    }
}
