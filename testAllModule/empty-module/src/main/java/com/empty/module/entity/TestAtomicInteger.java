package com.empty.module.entity;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author： Dingpengfei
 * @Description：用作测试AtomicInteger
 * @Date： 2020-4-17 16:15
 */
public class TestAtomicInteger {
    private static final int THREADS_COUNT = 2;

    public static int count = 0;
    public static volatile int countVolatile = 0;
    public static AtomicInteger atomicInteger = new AtomicInteger(0);
    public static CountDownLatch countDownLatch = new CountDownLatch(2);
    public static CountDownLatch countDownLatch2 = new CountDownLatch(1000);

    public static  void increase() {
        count++;
        countVolatile++;
        atomicInteger.incrementAndGet();
    }

    public static void main(String[] args) throws InterruptedException {
//       test1();
        test2();
    }

    private static void test2() throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                try {
                    Thread.sleep(111);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                increase();
                countDownLatch2.countDown();
            }).start();
        }
        countDownLatch2.await();
        System.out.println(count);
        System.out.println(countVolatile);
        System.out.println(atomicInteger.get());

    }

    //别人
    //对于计算机的话，是有内存和cpu的区别
    private static void test1() throws InterruptedException {
        Thread[] threads = new Thread[THREADS_COUNT];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int i1 = 0; i1 < 1000; i1++) {
                    increase();
                }
                countDownLatch.countDown();
            });
            threads[i].start();
        }

        countDownLatch.await();

        System.out.println(count);
        System.out.println(countVolatile);
        System.out.println(atomicInteger.get());
    }
}
