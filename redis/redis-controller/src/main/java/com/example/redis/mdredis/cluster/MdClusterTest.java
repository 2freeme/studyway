package com.example.redis.mdredis.cluster;

import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-7-13 17:57
 */
@Service
public class MdClusterTest {
    @Resource
    JedisCluster jedisCluster;
    private  void set (){
        jedisCluster.set("ke", "ai");
    }

}
