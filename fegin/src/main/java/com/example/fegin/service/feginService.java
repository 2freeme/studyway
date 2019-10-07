package com.example.fegin.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 暴露接口调用我们的客户端
 */
@FeignClient(value = "eurekaOne",fallback = HystrixFenclient.class)
public interface feginService {
    @RequestMapping("/test")
    public String CallSay(@RequestParam(value = "name") String name);
}
