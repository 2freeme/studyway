package com.test.module.transien.transienttes.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

/**
 * @Author： Dingpengfei
 * @Description： 测试
 * @Date： 2020-5-12 9:49
 */
public class Test {
    public static void main(String[] args) {
        String aa = "werwer";
        String note = (aa == null ? "" : aa + ", ") + "由CIMS财务单[]自动生成出库单";
        System.out.println(note);
        Integer a = 1;
        Integer B = 1;
        System.out.println(a == B);
        HashMap<String, Object> receivedMap = new HashMap<>();
        new BigDecimal("1");
        System.out.println(String.valueOf(receivedMap.get("receiveQty")));
        //new BigDecimal(null);
        System.out.println();
       // test2();
    }

    public static void test2() {
        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list,"1","2","3");
        list.add(1,"4" ); // 1  4   2   3 这个方法能直接的将数据存在某个固定位置，将其后面的数据往后推
            System.out.println();
        }


    }




