package com.base.util.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-8-14 11:39
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Student2 implements Serializable
{
    private int age ;
    private String name ;
    private int test;
}
