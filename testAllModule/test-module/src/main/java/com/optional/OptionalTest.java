package com.optional;

import com.alibaba.fastjson.JSONObject;

/**
 * @Author： Dingpengfei
 * @Description：测试 Optional 类
 * @Date： 2020-9-2 11:41
 */
public class OptionalTest {
    public static void main(String[] args) {

        JSONObject jsonObject = new JSONObject();
        System.out.println( "null".equals(jsonObject.getString("aa")));

    }
}
