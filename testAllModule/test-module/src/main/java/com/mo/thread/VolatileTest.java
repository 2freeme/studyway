package com.mo.thread;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2019/10/20 14:33
 */
public class VolatileTest {
    private  static volatile long  count =0l;
    private static final int NUMBER = 1000;

    public static void main(String[] args) {
        Thread substractThread = new SubtractThread();
        substractThread.start();
        for  (int i =0 ; i<NUMBER  ;i++) {
            count++;
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
