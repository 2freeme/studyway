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
}
