package com.empty.module.entity;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-3-22 22:12
 */
public class Stuent {
    private int  age;
    private String name;

    public Stuent(int age, String name) {
        this.age = age;
        this.name = name;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
