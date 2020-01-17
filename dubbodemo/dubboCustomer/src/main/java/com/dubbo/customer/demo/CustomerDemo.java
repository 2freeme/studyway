package com.dubbo.customer.demo;

import com.demo.interfence.test.ProvriderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-1-16 19:29
 */
@Controller
public class CustomerDemo {
    @Resource
    private ProvriderService provriderService;

    @RequestMapping("/test")
    private  String test(){
        System.out.println("进入到方法");

        return "success";
    }
}
