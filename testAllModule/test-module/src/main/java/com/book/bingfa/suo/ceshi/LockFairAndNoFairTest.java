package com.book.bingfa.suo.ceshi;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author： Dingpengfei
 * @Description：用作公平锁和非公平锁的性能测试
 * 结论：公平锁的是能保证公平的，能保证绝对的顺序 1000线程每个获取一次锁
 *      非公平锁能大致的保证顺序，也可能是我中间的执行比较快的原因但是还能能发现并不能很好的按顺序执行
 *      非公平锁的效率大约是公平锁的效率的2倍多  非公平： 1041  公平：445
 * 书中结论：
 *      测试：10个线程，每个线程获取100000次锁
 *      在测试中公平性锁与非公平性锁相比，总耗时是其94.3倍，总切换次数是其133倍。
 *      可以看出，公平性锁保证了锁的获取按照FIFO原则，而代价是进行大量的线程切换。
 *      非公平性锁虽然可能造成线程“饥饿”，但极少的线程切换，保证了其更大的吞吐量。
 * 注意：用线程的进去的顺序和打印的名字的顺序来判断是否有序
 * @Date： 2020-6-18 17:12
 */
public class LockFairAndNoFairTest {
    static ReentrantLock fair = new ReentrantLock(true);
    static ReentrantLock noFair = new ReentrantLock(false);
    static CountDownLatch countDownLatch = new CountDownLatch(1000);

    public static void main(String[] args) throws InterruptedException {
//        myTestFair();
        myTestNofair();
    }

    static void myTestNofair() throws InterruptedException {
        long l2 = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                System.out.println("线程又进来了" + Thread.currentThread().getName());
                noFair.lock();
                System.out.println(Thread.currentThread().getName());
                noFair.unlock();
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        System.out.println("第二次的时间" + (System.currentTimeMillis() - l2));  //第二次的时间445
      /*  线程又进来了Thread-36
        线程又进来了Thread-38
        线程又进来了Thread-37
        Thread-38
        Thread-36
        Thread-37*/
    }

    static void myTestFair() throws InterruptedException {
        long l1 = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                System.out.println("线程进来了" + Thread.currentThread().getName());
                fair.lock();
                System.out.println(Thread.currentThread().getName());
                fair.unlock();
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        System.out.println("第一次的时间" + (System.currentTimeMillis() - l1));// 第一次的时间1041
    }
}
