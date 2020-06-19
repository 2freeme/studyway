package com.book.bingfa.suo.ceshi;

import com.book.bingfa.suo.TwinsLock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;

/**
 * @Author： Dingpengfei
 * @Description：用作测试 TwinsLock
 * @Date： 2020-6-18 15:49
 * /**
 * 结论：我们再设计的时候，如果是TwinsLock的值设置为1的时候，可以很好的应付高并发的情况，设置成50的时候也会有少量的9998，说明也是可以的
 */
public class TwinsLockTest2 {
    static int count = 0;
    static CountDownLatch countDownLatch = new CountDownLatch(10000);


    public static void main(String[] args) throws InterruptedException {
        Lock lock = new TwinsLock();
        List<Thread> list = new ArrayList<>();

        for (int i = 0; i < 10000; i++) {
            Thread thread = new Thread(() ->
            {
                lock.lock();
                test();
                lock.unlock();
                countDownLatch.countDown();
            });

            list.add(thread);
        }

        for (Thread thread : list) {
            thread.start();
        }
        countDownLatch.await();
        System.out.println(count);
    }

    private static void test() {
        count++;

    }
}
