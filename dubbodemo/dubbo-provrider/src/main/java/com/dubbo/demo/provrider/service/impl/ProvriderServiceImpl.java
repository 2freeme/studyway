package com.dubbo.demo.provrider.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.demo.interfence.test.ProvriderService;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-2-1 16:16
 */
@Service
public class ProvriderServiceImpl implements ProvriderService {

    @Override
    public void dubboProvrider() {
        System.out.println("====进入了provrider的方法");
    }
}
