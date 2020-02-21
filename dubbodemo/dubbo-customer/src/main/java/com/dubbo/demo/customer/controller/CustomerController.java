package com.dubbo.demo.customer.controller;

import com.dubbo.demo.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-2-1 16:20
 */
@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @ResponseBody
    @RequestMapping("/test")
    private String test (){
        System.out.println("进入了controller");
        customerService.dubboCustomer();
        System.out.println("=====controller结束");
        return "success";
    }
}
