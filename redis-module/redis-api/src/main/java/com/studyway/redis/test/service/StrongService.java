package com.studyway.redis.test.service;

import com.studyway.redis.test.entity.Strong;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-3-24 17:52
 */


public interface StrongService {
    @PostMapping("/updateStrong")
    void updateStrong( @RequestBody Strong strong);
}
