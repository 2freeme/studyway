package com.studyway.redis.test.service;

import com.studyway.redis.test.entity.Strong;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-3-24 17:52
 */
@FeignClient(value = "redis_demo2")
public interface StrongService {
    @RequestMapping("updateStrong")
    void updateStrong(Strong strong);
}
