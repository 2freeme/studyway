package com.dubbo.dubbo.provrider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.demo.interfence.test.ProvriderService;
import org.springframework.stereotype.Component;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-3-10 16:11
 */
@Service   //这里引用的是dubbo的service是为了提供服务者
@Component
public class ProvriderServiceImpl implements ProvriderService {
    @Override
    public void dubboProvrider() {
        System.out.println("测试提供者");
    }
}
