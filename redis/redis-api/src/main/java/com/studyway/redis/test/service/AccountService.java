package com.studyway.redis.test.service;

import com.studyway.redis.test.entity.Account;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-3-24 17:50
 */

public interface AccountService {
    @PostMapping("/createAccountFlow")
    void createAccountFlow(@RequestBody Account account);
    @PostMapping("/createAccountFlow2") //不配置的话会404
    void createAccountFlow2(@RequestBody Account account);
}
