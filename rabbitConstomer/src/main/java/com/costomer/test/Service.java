package com.costomer.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2019/7/27 22:16
 */
@org.springframework.stereotype.Service
public class Service {
    @Autowired
    RestTemplate restTemplate;

    @com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand(fallbackMethod = "Hierror")
    public String Test(String name) throws Exception {
        if (name.equals("1"))
        throw new  Exception();
         restTemplate.getForObject("http://EUREKAONE/test?name="+name, String.class);
        return "1";
    }

    public String Hierror(String name) {
        System.out.println("进入了Hierror的方法，出错了");
        return "出错了";
    }


}
