package com.book.jvm.gc;

/**
 * @Author： Dingpengfei
 * @Description：测试计数引用算法的漏洞 计数引用算法：引用一次 +1 ，取消引用 -1    ===0时候可以gc
 * 这里的运行的结果就是 垃圾回收期可以正常的回收
 * 这样子看的话，就会java的虚拟机所引用的就不是这个算法了
 *
 * @Date： 2020-7-3 13:52
 */
public class NoGCTest {
    public Object instance = null;
    private static final int _1MB = 1024 * 1024;

    /**
     * 比较占用内存，以便能在GC日志中看清楚是否有回收
     */
    private byte[] bigSize = new byte[2 * _1MB];

    public static void testGc() {
        NoGCTest oa = new NoGCTest();
        NoGCTest ob = new NoGCTest();
        oa.instance = ob;
        ob.instance = oa;
        oa = null;
        ob = null;
        //手动触发GC
        System.gc();
    }

    public static void main(String[] args) {
        testGc();
    }


}
