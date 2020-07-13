package com.example.redis.rpcutil.impl;

import com.example.redis.rpcutil.RedisController;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by zhaolp on 2017/6/30 0030.
 */
@Repository
public class  DefaultRedisController implements RedisController  {
    /**
     * 引入RedisConfig中的 jedisCluster Bean
     */
    @Resource
    private JedisCluster jedisCluster;

    @Override
    public String get(String key) {
        return jedisCluster.get(key);
    }

    @Override
    public String set(String key, String value) {
        return jedisCluster.set(key, value);
    }

    @Override
    public String hget(String hkey, String field) {
        return jedisCluster.hget(hkey, field);
    }

    @Override
    public Long hset(String hkey, String field, String value) {
        return jedisCluster.hset(hkey, field, value);
    }

    @Override
    public Long incr(String key) {
        return jedisCluster.incr(key);
    }

    /**
     * 将 key 所储存的值加上给定的增量值（increment） 。
     * @param key
     * @param integer
     * @return
     */
    public Long incrBy(String key, long integer){
        return jedisCluster.incrBy(key,integer);
    }

    @Override
    public Long expire(String key, int sec) {
        return jedisCluster.expire(key, sec);
    }

    @Override
    public Long ttl(String key) {
        return jedisCluster.ttl(key);
    }

    @Override
    public Long del(String key) {
        return jedisCluster.del(key);
    }

    @Override
    public Long hdel(String hkey, String field) {
        return jedisCluster.hdel(hkey, field);
    }

    @Override
    public Long sadd(String key, String... member) {
        return jedisCluster.sadd(key, member);
    }

    @Override
    public Long sadd(String key, int seconds, String... member) {
        Long count = sadd(key, member);
        expire(key, seconds);
        return count;
    }

    @Override
    public Set<String> smembers(String key) {
        return jedisCluster.smembers(key);
    }

    @Override
    public Long smove(String srcKey, String dstKey, String member) {
        return jedisCluster.smove(srcKey, dstKey, member);
    }

    @Override
    public Long srem(String key, String... member) {
        return jedisCluster.srem(key, member);
    }

    @Override
    public Long sunionstore(String destination, String... key) {
        return jedisCluster.sunionstore(destination, key);
    }

    @Override
    public Long scard(String key) {
        return jedisCluster.scard(key);
    }

    @Override
    public Long rpush(String key, String... string) {
        return jedisCluster.rpush(key, string);
    }

    @Override
    public Long rpush(String key, int seconds, String... string) {
        Long count = rpush(key, string);
        expire(key, seconds);
        return count;
    }

    @Override
    public Long lrem(String key, long count, String value) {
        return jedisCluster.lrem(key, count, value);
    }

    @Override
    public List<String> lrange(String key, long start, long end) {
        return jedisCluster.lrange(key, start, end);
    }

    @Override
    public String rename(String oldKey, String newKey) {
        return jedisCluster.rename(oldKey, newKey);
    }

    @Override
    public Set<String> keys (String pattern) {
        Map<String, JedisPool> clusterNodes = jedisCluster.getClusterNodes();
        Set<String> result = new HashSet<>();
        for (Map.Entry<String, JedisPool> entry : clusterNodes.entrySet()) {
            Jedis jedis = entry.getValue().getResource();
            // 判断非从节点(因为若主从复制，从节点会跟随主节点的变化而变化)
            if (!jedis.info("replication").contains("role:slave")) {
                // 搜索单个主节点内匹配的Key
                Set<String> keys = jedis.keys(pattern);
                if (keys != null && keys.size() > 0) {
                    result.addAll(keys);
                }
            }
            jedis.close();
        }
        return result;
    }
}
