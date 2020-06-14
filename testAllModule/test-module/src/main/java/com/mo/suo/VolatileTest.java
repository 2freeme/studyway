package com.mo.suo;

/**
 * @Author： Dingpengfei
 * @Description： volatile  保持线程而可见性   禁止指令重排序
 * 使用cpu的缓存一致性协议
 * @Date： 2019/10/20 14:33
 */
public class VolatileTest {
    private  static volatile long  count =0l;
    private static final int NUMBER = 1000;

    public static void main(String[] args) {
        Thread substractThread = new SubtractThread();
        substractThread.start();
        for  (int i =0 ; i<NUMBER  ;i++) {
            count++;  //count++并不是原子性的， 一般volitile能保证可见性，但是不能保证原子性，所以高并发还是有问题
            // 2个线程都读了1  但是都进行了++  因为不是原子性的  所以还是需要加锁

        }
        while (substractThread.isAlive()){
            System.out.println("count最后的值："+count);
        }
    }

    private static class SubtractThread extends Thread{
        @Override
        public   void run() {
            for (int i= 0 ; i<NUMBER ;i++) {
                count --;
            }
        }
    }
}
