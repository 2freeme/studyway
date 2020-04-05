package com.example.redis.demo1.service;

import com.studyway.redis.test.entity.Account;
import com.studyway.redis.test.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-3-24 19:42
 */
@Service
@RestController  //必须的因为是http的请求 所以需要  不然调用的时候会404
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountMapper accountMapper;

    @Override
    @PostMapping("/createAccountFlow")
    public void createAccountFlow(Account account) {
        System.out.println("com.example.redis.demo1.service.AccountServiceImpl.createAccountFlow"+account.toString());
        accountMapper.saveAccountFlow(account);
    }
}
