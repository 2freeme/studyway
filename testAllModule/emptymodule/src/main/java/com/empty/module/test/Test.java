package com.empty.module.test;

import com.empty.module.entity.Stuent;

/**
 * @Author： Dingpengfei
 * @Description：测试特殊的tostring的方法
 * @Date： 2020-3-22 22:13
 */
public class Test {

    public static void main(String[] args) {
        Stuent stuent = new Stuent(1,"1");
        System.out.println(stuent.toString());
//        com.empty.module.entity.Stuent@63947c6b[age=1,name=1]
//
    }
}
