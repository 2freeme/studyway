package com;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/**
 * @Author： Dingpengfei
 * @Description： 启动的时候value的序列化
 * @Date： 2020/7/12 21:47
 */

@Configuration
public class MyConfig {
    @Bean
    public StringRedisTemplate xxxx(RedisConnectionFactory fc) {
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(fc);
        stringRedisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class));
        return stringRedisTemplate;
    }
}
