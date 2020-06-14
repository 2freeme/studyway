package com.mo.suo;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author： Dingpengfei
 * @Description：Atomic 类的测试
 * Atomic  是使用CAS的自旋来比较的，无需加锁,超高并发也可以支持
 * @Date： 2020/6/14 10:12
 */
public class AtomicTest {
    static AtomicInteger in = new AtomicInteger(0);

    /*synchronized*/ void m() {
        in.incrementAndGet();  //无锁  原子操作，中间不会被修改
    }

    /**
     * 整个的逻辑就是 创建10000个线程，然后同时执行，在然后的话就是使其挨个的执行结束
     *
     * @param args
     */
    public static void main(String[] args) {

        AtomicTest a = new AtomicTest();
        ArrayList<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 100000; i++) {
            threads.add(new Thread(a::m));
        }
        threads.forEach((o) -> o.start());
        threads.forEach((o) -> {
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(in.intValue());  //100000 加不加syn都一样的
    }

}
