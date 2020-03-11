package com.example.dubbo.customer.service.impl;

import com.example.dubbo.customer.service.CustomerService;
import org.springframework.stereotype.Service;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-3-11 15:59
 */
@Service
public class CustomerServiceImpl implements CustomerService {

//    @Reference /// 是dubbo的注解，这里就是为了自动的去寻找dubbo提供的服务者
//    ProvriderService provriderService;

    public void test() {
        System.out.println(111);
//        provriderService.dubboProvrider();
    }
}
