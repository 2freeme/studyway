package com.example.redis.lock.reentrant.minelock;

/**
 * @Author： Dingpengfei
 * @Description：  这个涉及的就是不可重入锁的例子
 * @Date： 2020-5-11 20:50
 */
public class NoreenTrantCount {
    private static NoreenTrantLock lock =new NoreenTrantLock();

    public static void print() throws InterruptedException {
        lock.lock();
        doAdd();
        lock.unlock();
    }

    public static void doAdd() throws InterruptedException {
        lock.lock();
        //do something
        lock.unlock();

    }

    public static void main(String[] args) throws InterruptedException {
        print();
        System.out.println(1111111111111l);
    }
}