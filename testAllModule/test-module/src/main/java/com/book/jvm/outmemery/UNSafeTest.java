package com.book.jvm.outmemery;

import sun.misc.Unsafe;

import java.lang.reflect.Field;


/**
 * @Author： Dingpengfei
 *
 * @Description：测试书中的案列  内存溢出
 * VM Args：-Xmx20M -XX:MaxDirectMemorySize=10M
 * @Date： 2020-7-3 9:55
 */
public class UNSafeTest {
    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) throws IllegalAccessException {
        Field unsafe = Unsafe.class.getDeclaredFields()[0];
        unsafe.setAccessible(true);
        Unsafe o = (Unsafe)unsafe.get(null);
        while (true){
            o.allocateMemory(_1MB);
        }
    }

}
