package com.studyway.redis.test.service;

import com.studyway.redis.test.entity.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-3-24 17:50
 */
@FeignClient(value = "redis_demo1")
public interface AccountService {
    @RequestMapping("createAccountFlow")
    void createAccountFlow(Account account);
}
