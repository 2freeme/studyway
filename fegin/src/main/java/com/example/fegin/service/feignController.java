package com.example.fegin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2019/7/28 2:12
 */
@RestController
public class feignController {
    @Autowired
    feginService service;

    @RequestMapping("/hi")
    public String feignTest(String name) {
        System.out.println("进入了feign的方法" + name);
        return service.CallSay( name);

    }
}
