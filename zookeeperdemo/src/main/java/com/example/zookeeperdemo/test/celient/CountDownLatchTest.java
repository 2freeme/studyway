package com.example.zookeeperdemo.test.celient;

import java.util.concurrent.CountDownLatch;

/**
 * @Author： Dingpengfei
 * @Description：用来测试CountDownLatch的使用
 * 对于CountDownLatch来说就是一个信号量的意思，你赋予一个值，将一个线程挂起，等信号量为0的时候会自动的触发。
 *
 * @Date： 2020-3-11 23:11
 */
public class CountDownLatchTest {
    private final static CountDownLatch countDownLatch= new CountDownLatch(100);
    //TODO
    //调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行
    //public void await() throws InterruptedException { };
    //和await()类似，只不过等待一定的时间后count值还没变为0的话就会继续执行
    //public boolean await(long timeout, TimeUnit unit) throws InterruptedException { };
    //将count值减1
    //public void countDown() { };
    //TODO
    private static int count =0;
    public static void main(String[] args) throws InterruptedException {
        for (int i=0 ;i<100;i++){
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(count);
                    count++;
                }
            });
            thread.start();
            countDownLatch.countDown();
        }
        countDownLatch.await();
        System.out.println("主线程结束");
    }
}
