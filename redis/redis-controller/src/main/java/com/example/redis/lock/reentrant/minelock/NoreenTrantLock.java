package com.example.redis.lock.reentrant.minelock;

/**
 * @Author： Dingpengfei
 * @Description： 不可重入锁  手写测试类
 * @Date： 2020-5-11 20:43
 */
public class NoreenTrantLock {
    private boolean isLocked = false;

    public synchronized void lock() throws InterruptedException {
        while (isLocked) {
            wait();
        }
        isLocked = true;
    }

    public synchronized void unlock() {
        isLocked = false;
        notify();
    }
}