package com.example.redis.rpcutil.impl;

import com.example.redis.rpcutil.RedisController;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * Created by zhaolp on 2017/7/1 0001.
 */
@Repository
public class SingleRedisController implements RedisController {
    @Resource
    private JedisPool jedisPool;

    @Override
    public String get(String key) {
        Jedis resource = jedisPool.getResource();
        String s = resource.get(key);
        resource.close();
        return s;
    }

    @Override
    public String set(String key, String value) {
        Jedis resource = jedisPool.getResource();
        String set = resource.set(key, value);
        resource.close();
        return set;
    }

    @Override
    public String hget(String hkey, String field) {
        Jedis resource = jedisPool.getResource();
        String hget = resource.hget(hkey, field);
        resource.close();
        return hget;
    }

    @Override
    public Long hset(String hkey, String field, String value) {
        Jedis resource = jedisPool.getResource();
        Long hset = resource.hset(hkey, field, value);
        resource.close();
        return hset;
    }

    @Override
    public Long incr(String key) {
        Jedis resource = jedisPool.getResource();
        Long incr = resource.incr(key);
        resource.close();
        return incr;
    }

    @Override
    public Long incrBy(String key, long integer) {
        Jedis resource = jedisPool.getResource();
        Long incr = resource.incrBy(key,integer);
        resource.close();
        return incr;
    }

    @Override
    public Long expire(String key, int sec) {
        Jedis resource = jedisPool.getResource();
        Long expire = resource.expire(key, sec);
        resource.close();
        return expire;
    }

    @Override
    public Long ttl(String key) {
        Jedis resource = jedisPool.getResource();
        Long ttl = resource.ttl(key);
        resource.close();
        return ttl;
    }

    @Override
    public Long del(String key) {
        Jedis resource = jedisPool.getResource();
        Long del = resource.del(key);
        resource.close();
        return del;
    }

    @Override
    public Long hdel(String hkey, String field) {
        Jedis resource = jedisPool.getResource();
        Long hdel = resource.hdel(hkey, field);
        resource.close();
        return hdel;
    }

    @Override
    public Long sadd(String key, String... members) {
        Jedis resource = jedisPool.getResource();
        Long count = resource.sadd(key, members);
        resource.close();
        return count;
    }

    @Override
    public Long sadd(String key, int seconds, String... members) {
        Jedis resource = jedisPool.getResource();
        Long count = resource.sadd(key, members);
        resource.expire(key, seconds);
        resource.close();
        return count;
    }

    @Override
    public Set<String> smembers(String key) {
        Jedis resource = jedisPool.getResource();
        Set<String> result = resource.smembers(key);
        resource.close();
        return result;
    }

    @Override
    public Long smove(String srcKey, String dstKey, String members) {
        Jedis resource = jedisPool.getResource();
        Long count = resource.smove(srcKey, dstKey, members);
        resource.close();
        return count;
    }

    @Override
    public Long srem(String key, String... members) {
        Jedis resource = jedisPool.getResource();
        Long count = resource.srem(key, members);
        resource.close();
        return count;
    }

    @Override
    public Long sunionstore(String destination, String... key) {
        Jedis resource = jedisPool.getResource();
        Long result = resource.sunionstore(destination, key);
        resource.close();
        return result;
    }

    @Override
    public Long scard(String key) {
        Jedis resource = jedisPool.getResource();
        Long result = resource.scard(key);
        resource.close();
        return result;
    }

    @Override
    public Long rpush(String key, String... string) {
        Jedis resource = jedisPool.getResource();
        Long count = resource.rpush(key, string);
        resource.close();
        return count;
    }

    @Override
    public Long rpush(String key, int seconds, String... string) {
        Jedis resource = jedisPool.getResource();
        Long count = resource.rpush(key, string);
        resource.expire(key, seconds);
        resource.close();
        return count;
    }

    @Override
    public Long lrem(String key, long count, String value) {
        Jedis resource = jedisPool.getResource();
        Long result = resource.lrem(key, count, value);
        resource.close();
        return result;
    }

    @Override
    public List<String> lrange(String key, long start, long end) {
        Jedis resource = jedisPool.getResource();
        List<String> result = resource.lrange(key, start, end);
        resource.close();
        return result;
    }

    @Override
    public String rename(String oldKey, String newKey) {
        Jedis resource = jedisPool.getResource();
        String result = resource.rename(oldKey, newKey);
        resource.close();
        return result;
    }

    @Override
    public Set<String> keys(String pattern) {
        Jedis resource = jedisPool.getResource();
        Set<String> keys = resource.keys(pattern);
        resource.close();
        return keys;
    }
}
