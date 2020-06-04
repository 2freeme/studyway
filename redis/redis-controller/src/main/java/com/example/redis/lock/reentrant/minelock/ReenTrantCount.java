package com.example.redis.lock.reentrant.minelock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author： Dingpengfei
 * @Description： 这里是可重入锁的测试
 * @Date： 2020-5-12 8:33
 */
public class ReenTrantCount {
    private static ReenTrantLock lock = new ReenTrantLock();
    private static int count = 0;

    public static void print() throws InterruptedException {
        lock.lock();
        doAdd();
        lock.unlock();
    }

    public static void doAdd() throws InterruptedException {
        lock.lock();
        System.out.println(count);
        count++;
        lock.unlock();

    }

    public static void main(String[] args) throws InterruptedException {
        print();
        print();
        print();
        print();

        System.out.println(1111111111111l);
    }
}
