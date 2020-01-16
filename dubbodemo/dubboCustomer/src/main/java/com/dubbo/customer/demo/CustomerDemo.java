package com.dubbo.customer.demo;

import com.demo.interfence.test.ProvriderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-1-16 19:29
 */
@Controller
public class CustomerDemo {
    @Autowired
    private ProvriderService provriderService;

    @RequestMapping("/test")
    private  String test(){
        System.out.println("进入到方法");

        return "success";
    }
}
