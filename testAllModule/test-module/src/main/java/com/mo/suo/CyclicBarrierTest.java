package com.mo.suo;

import java.util.concurrent.CyclicBarrier;

/**
 * @Author： Dingpengfei
 * @Description： 满人发车类型的测试  可以做限流使用  但是一般的话的限流使用的就是Guava  RateLiminter
 * 就是在一个类中加上满人的情况，然后一起执行
 * 一般的话来说做并发操作，必须一起执行的情况
 * @Date： 2020/6/14 11:36
 */
public class CyclicBarrierTest {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(20, () -> System.out.println("满人，开始发车"));
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                try {
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();

        }
    }

}
