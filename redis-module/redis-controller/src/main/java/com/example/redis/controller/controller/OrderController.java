package com.example.redis.controller.controller;

import com.example.redis.controller.service.OrderService;
import com.studyway.redis.test.entity.Myorder;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    OrderService orderService ;
    @RequestMapping("/test")
    public String test(){
        Myorder myOrder = new Myorder();
        myOrder.setItemName("手机");
        myOrder.setQty(1);
        myOrder.setUserName("dingpf1");
        orderService.submitOrder(myOrder);
        System.out.println();
        return "success";
    }
}
