package com.mo.suo;

import java.util.concurrent.Semaphore;

/**
 * @Author： Dingpengfei
 * @Description： 灯塔  同时几个执行的意思  限流
 * 分批发短信
 *
 * @Date： 2020/6/14 13:31
 */
public class SemaphoreTest {

    /**
     * 三个线程，使用灯塔  使其2个同时执行。
     * 有些有释放 有些未释放 看有没有差别
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore =new Semaphore(2); //同时允许2个同时执行
       // Semaphore semaphore2 =new Semaphore(2,true); //默认不公平 。 内部有队列 AQS
        new Thread(()->{
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                //semaphore.release();
            }
            System.out.println("111111111111111");
        }).start();

        new Thread(()->{
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                //semaphore.release();

            }
            System.out.println("222222222222222222");
        }).start();

        new Thread(()->{
            try {
                semaphore.acquire(); //这里如果拿不到的话 会一直的阻塞
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                //semaphore.release();
            }
            System.out.println("333333333333333"); //注：未释放的时候  不会将这一句打出来。因为未释放
        }).start();


        Thread.sleep(10000);
    }
}
