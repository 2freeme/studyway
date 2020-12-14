package com.example.demo.config;

import com.example.demo.service.RefundOrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;



@Component
public class ApplicationRunnerImpl implements ApplicationRunner {

    @Autowired
    private RefundOrderServiceImpl refundOrderService;

    //tomcat启动执行此方法
    @Override
    public void run(ApplicationArguments args) throws Exception {
        refundOrderService.refundOrder();
    }
}
