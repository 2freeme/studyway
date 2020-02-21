package com.dubbo.demo.customer.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.dubbo.demo.customer.service.CustomerService;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-2-1 16:15
 */
@Service
public class CustomerServiceImpl implements CustomerService {
    @Override
    public void dubboCustomer() {
        System.out.println("=====进入了customer的方法");
    }
}
