package com.example.fegin.service;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2019/7/28 8:44
 */
@Component
public class HystrixFenclient implements feginService {

    @Override
    public String CallSay(String name) {
        System.out.println("我们的feign出错了，进入了断路器");
        return "error";

    }
}
