package com.costomer.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2019/7/28 1:14
 */
@RestController
public class Controller {
    @Autowired
    private Service service;

    @RequestMapping("/hi")
    public String hi(@RequestParam String name) throws Exception {
        System.out.println("ribbon调用成功");
        return service.Test(name);

    }
}
