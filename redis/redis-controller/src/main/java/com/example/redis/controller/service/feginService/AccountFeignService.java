package com.example.redis.controller.service.feginService;

import com.studyway.redis.test.service.AccountService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-3-26 18:59
 */
@FeignClient(value = "redis-demo1")
public interface AccountFeignService extends AccountService {
}
