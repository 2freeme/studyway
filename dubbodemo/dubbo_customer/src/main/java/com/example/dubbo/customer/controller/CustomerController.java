package com.example.dubbo.customer.controller;

import com.example.dubbo.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-3-11 16:01
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;
    @RequestMapping("/test")
    public String test(){
        customerService.test();
        return "cuccess";
    }
}
