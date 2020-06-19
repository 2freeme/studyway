package com.book.bingfa.suo.ceshi;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author： Dingpengfei
 * @Description：用作读写锁的测试 所有的lock都是阻塞的 ，会无限的循环的。
 * @Date： 2020-6-19 14:22
 */
public class ReadAndWriteLockTest {
    static ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    static int count = 1;
    static CountDownLatch countDownLatch = new CountDownLatch(100);

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                writeTest();
//                readTest();
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
    }

    public static void writeTest() {
        lock.writeLock().lock();

        System.out.println("写入的锁===" + (count++));

    }

    public static void readTest() {
        lock.readLock().lock();
        System.out.println("读出来的count=" + count);

    }
}
