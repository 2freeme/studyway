package com.mo.suo;

/**
 * @Author： Dingpengfei
 * @Description：  测试volitile的可见性
 * volatile  保持线程而可见性   禁止指令重排序
 *
 * @Date： 2019/10/20 14:33
 */
public class VolatileTest2 {
    private static volatile long count = 0l;
    private static final int NUMBER = 1000;
    /*volatile*/ static boolean count1 = true; //有的加 有的不加

    public static void main(String[] args) {
        new Thread(() -> m()).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(1000);
        count1 = false;


    }

    public static void m() {
        long l = System.currentTimeMillis();
        System.out.println("m开始");
        while (count1) {

        }
        System.out.println("m结束" + (System.currentTimeMillis() - l));
        //加 m结束1001
        //不加的话  1分钟类还未有回应。。。。。。
    }
}
