package com.dubbo.dubbo.provrider.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.demo.interfence.test.Provrider2Service;
import org.springframework.stereotype.Component;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-3-11 18:09
 */
@Service
@Component
public class Provrider2ServiceImpl implements Provrider2Service {
    @Override
    public void test() {
        System.out.println(1);
    }
}
