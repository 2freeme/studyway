package com.dubbo.provrider.demo;

import com.alibaba.dubbo.config.annotation.Service;
import com.demo.interfence.test.ProvriderService;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-1-16 17:34
 */
@Service
public class ProvriderServiceImpl implements ProvriderService {
    public void dubboProvrider() {
        System.out.println("hi,provrider 被调用了啊");
    }
}
