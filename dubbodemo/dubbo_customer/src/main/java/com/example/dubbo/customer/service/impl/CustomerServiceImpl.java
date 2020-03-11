package com.example.dubbo.customer.service.impl;

import com.demo.interfence.test.ProvriderService;
import com.example.dubbo.customer.service.CustomerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-3-11 15:59
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    @Resource ///不可用@Autoried  自动连接服务去找
    ProvriderService provriderService;

    public void test() {
        System.out.println(111);
        provriderService.dubboProvrider();
    }
}
