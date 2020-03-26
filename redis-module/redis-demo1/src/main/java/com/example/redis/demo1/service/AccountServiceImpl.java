package com.example.redis.demo1.service;

import com.example.redis.demo1.dao.AccountMapper;
import com.studyway.redis.test.entity.Account;
import com.studyway.redis.test.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-3-24 19:42
 */
@Service
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountMapper accountMapper;

    @Override
    public void createAccountFlow(Account account) {
        accountMapper.saveAccountFlow(account);
    }
}
