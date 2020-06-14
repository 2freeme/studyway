package com.mo.suo;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-2-21 11:25
 */
public class Test {
    public static void main(String[] args) {
        Integer a=null;
        //System.out.println(a.intValue());
        HashMap<Object, Object> objectObjectHashMap = new HashMap<Object, Object>();
        objectObjectHashMap.put("defaultRate", "112");
        BigDecimal bigDecimal = new BigDecimal(objectObjectHashMap.get("defaultRate") == null ? "0" : objectObjectHashMap.get("defaultRate").toString());
        System.out.println(bigDecimal);

    }
}
