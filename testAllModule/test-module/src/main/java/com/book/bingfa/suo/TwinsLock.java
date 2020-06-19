package com.book.bingfa.suo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @Author： Dingpengfei
 * @Description：该工具在同一时刻，只允许至多两个线程同时访问，超过两个线程的
 * 访问将被阻塞，我们将这个同步工具命名为TwinsLock。
 * @Date： 2020-6-18 11:42
 */

/**
 * 首先，确定访问模式。
 * TwinsLock能够在同一时刻支持多个线程的访问，这显然是共享式访问，
 * 因此，需要使用同步器提供的acquireShared(int args)方法等和Shared相关的方法，
 * 这就要求TwinsLock必须重写tryAcquireShared(int args)方法和tryReleaseShared(int args)方法，
 * 这样才能保证同步器的共享式同步状态的获取与释放方法得以执行。
 */

/**
 * 定义资源数。
 * TwinsLock在同一时刻允许至多两个线程的同时访问，表明同步资源数为2，
 * 这样可以设置初始状态status为2，当一个线程进行获取，status减1，该线程释放，则status加1，
 * 状态的合法范围为0、1和2，其中0表示当前已经有两个线程获取了同步资源，此时再有其他线程对同步状态进行获取，
 * 该线程只能被阻塞。
 * 在同步状态变更时，需要使用compareAndSet(int expect,int update)方法做原子性保障
 */

/**
 * 自定义同步组件通过组合自定义同步器来完成同步功能，
 * 一般情况下自定义同步器会被定义为自定义同步组件的内部类。
 */
public class TwinsLock implements Lock {

    private final Sync sync = new Sync(1);

    private static final class Sync extends AbstractQueuedSynchronizer {
        Sync(int count) {
            if (count <= 0) {
                throw new IllegalArgumentException("count mast lager than zero");
            }
            setState(1);
        }

        public int tryAcquireShared(int reduceCount) {
            for (; ; ) {
                int current = getState();
                int newCount = current - reduceCount;
                if (newCount < 0 || compareAndSetState(current, newCount)) {
                    return newCount;
                }
            }
        }

        public boolean tryReleaseShared(int returnCount) {
            for (; ; ) {
                int current = getState();
                int newCount = current + returnCount;
                if (compareAndSetState(current, newCount)) {
                    return true;
                }
            }
        }
    }

    @Override
    public void lock() {
        sync.tryAcquireShared(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        sync.tryReleaseShared(1);
    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {

    }

    @Override
    public Condition newCondition() {
        return null;
    }



}
