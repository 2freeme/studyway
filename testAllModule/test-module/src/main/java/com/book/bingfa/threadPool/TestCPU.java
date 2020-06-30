package com.book.bingfa.threadPool;

/**
 * @Author： Dingpengfei
 * @Description：获取cpu的核数 基于这个可以配置线程池
 * @Date： 2020-6-24 11:50
 */
public class TestCPU {


    public static void main(String[] args) {
        test();
    }

    public static void test() {
        //打印出来的就是cpu的核数，基于这个可以配置线程池
         System.out.println(Runtime.getRuntime().availableProcessors());
    }
}
