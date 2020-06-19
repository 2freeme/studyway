package com.com.book.bingfa.suo;

import com.book.bingfa.suo.TwinsLock;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;

/**
 * @Author： Dingpengfei
 * @Description：用做测试
 * @Date： 2020-6-18 15:11
 */
public class TwinsLockTest {
    static int count2 = 0;

    @Test
    public void test() {
        final Lock lock = new TwinsLock();
        class ThreadTest extends Thread {
            @Override
            public void run() {
                while (true) {
                    lock.lock();
                    try {
                        Thread.sleep(1000);

                        System.out.println(Thread.currentThread().getName());
                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        lock.unlock();
                    }

                }
            }
        }
        // 启动10个线程
        for (int i = 0; i < 10; i++) {
            ThreadTest w = new ThreadTest();
            w.setDaemon(true);
            w.start();
        }
        // 每隔1秒换行
        for (int i = 0; i < 11; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println();

        }

    }

    @Test
    public  void test2() throws InterruptedException {
        Lock lock = new TwinsLock();
        CountDownLatch countDownLatch = new CountDownLatch(1000);
        int  count = 1;
        for (int i = 0; i < 1000; i++) {
            new Thread(() ->
            {
                lock.lock();
                test3(count);
                lock.unlock();
                countDownLatch.countDown();
            }).start();
        }
        countDownLatch.await();
        System.out.println(count);
    }

    private  int test3(int count) {
       return count++;
    }
}

