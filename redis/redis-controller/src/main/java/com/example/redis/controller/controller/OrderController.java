package com.example.redis.controller.controller;

import com.example.redis.controller.dao.MyorderDao;
import com.example.redis.controller.service.OrderService;
import com.example.redis.controller.service.RedisTestService;
import com.example.redis.controller.tran.TransService;
import com.example.redis.controller.lock.redislock.RedisLock;
import com.studyway.redis.test.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author： Dingpengfei
 * @Description： 优化成具体的controller 具体测试是在具体的service类中进行
 * @Date： 2020-3-24 17:40
 */
@ResponseBody
@RestController
@RequestMapping("/test")
public class OrderController {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(OrderController.class);

    @Autowired
    OrderService orderService;
    @Autowired
    AccountService accountService;
    @Autowired
    MyorderDao myorderDao;
    @Autowired
    TransService transService;
    @Autowired
    RedisTestService redisTestService;


    @Autowired
    RedisLock redisLock;

    @RequestMapping("/test")
    public String test() {
        //测试redis的回滚
//        redisTestService.testRedisRollBack();

        transService.transCeshi1();
        return "success";
    }


}
