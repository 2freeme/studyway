package com.example.redis.controller.service.feginService;

import com.studyway.redis.test.service.StrongService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-3-26 19:01
 */
@FeignClient(value = "redis-demo2")
public interface StrongFeignService extends StrongService {
}
