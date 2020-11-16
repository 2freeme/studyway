package com.test.result;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author：
 * @Description：
 * @Date： 2020-11-8 23:29
 */
public  class TestResult<T> {
    public static void main(String[] args) {
        Map< String,Integer> map  = new HashMap<>();
        Map< String,Integer> map2  = new HashMap<>();
        map2.put("a",111);
        map2.put("b",222);

        map.put("b",333 );
        for (Map.Entry<String, Integer> integerEntry : map2.entrySet()) {
            map.merge(integerEntry.getKey(),integerEntry.getValue() ,Integer::sum );

        }
        System.out.println(JSON.toJSONString(map));



    }
    public static  <T>T  test(ResultTest resultTest ,Class<T> list){
        if (resultTest.equals("000000")) throw new RuntimeException();
        if (resultTest.getData()==null)
        throw new RuntimeException();
        return  (T)resultTest.getData();
    }
}
