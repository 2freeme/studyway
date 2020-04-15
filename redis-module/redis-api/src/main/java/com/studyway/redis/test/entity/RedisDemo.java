package com.studyway.redis.test.entity;

import lombok.Data;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-4-6 22:44
 */
@Data
public class RedisDemo
{
    private String key;
    private String value;

    public RedisDemo(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "RedisDemo{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
