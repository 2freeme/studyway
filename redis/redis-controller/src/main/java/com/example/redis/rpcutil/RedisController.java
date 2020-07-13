package com.example.redis.rpcutil;

import java.util.List;
import java.util.Set;

/**
 * Created by zhaolp on 2017/6/30 0030.
 */
public interface RedisController {
    /**
     *
     * @param key
     * @return
     */
    String get(String key);

    /**
     * 设置指定 key 的值
     * @param key
     * @param value
     * @return
     */
    String set(String key, String value);

    /**
     * 获取存储在哈希表中指定字段的值
     * @param hkey
     * @param field
     * @return
     */
    String hget(String hkey, String field);

    /**
     * 将哈希表 key 中的字段 field 的值设为 value
     * @param hkey
     * @param field
     * @param value
     * @return
     */
    Long hset(String hkey, String field, String value);

    /**
     * 将 key 中储存的数字值增一
     * @param key
     * @return
     */
    Long incr(String key);

    /**
     * 将 key 所储存的值加上给定的增量值（increment） 。
     * @param key
     * @param integer
     * @return
     */
    Long incrBy(String key, long integer);
    /**
     * 为给定 key 设置过期时间
     * @param key
     * @param sec seconds 秒
     * @return
     */
    Long expire(String key, int sec);

    /**
     * 以秒为单位，返回给定 key 的剩余生存时间(TTL, time to live)。
     * @param key
     * @return
     */
    Long ttl(String key);

    /**
     * 该命令用于在 key 存在时删除 key
     * @param key
     * @return
     */
    Long del(String key);

    /**
     * 删除一个哈希表字段
     * @param hkey
     * @param field
     * @return
     */
    Long hdel(String hkey, String field);

    Long sadd(String key, String... member);

    Long sadd(String key, int seconds, String... member);

    Set<String> smembers(String key);

    Long smove(String srcKey, String dstKey, String member);

    Long srem(String key, String... member);

    Long sunionstore(String destination, String... key);

    Long scard(String key);

    Long rpush(String key, String... string);

    Long rpush(String key, int seconds, String... string);

    Long lrem(String key, long count, String value);

    List<String> lrange(String key, long start, long end);

    String rename(String oldKey, String newKey);

    /**
     * 模糊查询 keys
     * @param pattern
     * @return
     */
    Set<String> keys(String pattern);
}
