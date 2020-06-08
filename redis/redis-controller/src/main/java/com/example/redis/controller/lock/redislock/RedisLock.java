package com.example.redis.controller.lock.redislock;

import com.studyway.redis.test.entity.RedisDemo;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-4-6 22:40
 */
public interface RedisLock
{
    void setRedis(RedisDemo redisDemo);
    String getRedis(RedisDemo redisDemo);
}




