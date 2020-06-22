package com.book.bingfa.morethread;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @Author： Dingpengfei
 * @Description：用来测试fork/join框架的使用(用于拆分任务，可以设置多个的任务，开启线程，每一个已完成的任务)
 * 阈值为2 每一个小任务的都是单独的任务
 *
 * 结论 100000 已下都是可以对的上的，时间相差不是很大。
 * @Date： 2020-6-22 17:32
 */
@Deprecated  //有问题，但不做过多的深入
public class CountTask extends RecursiveTask<Integer> {
    private static final int YUZHI = 2;
    private static int start;
    private static int end;
    static  int sump;
    private int temp;

    public CountTask(int stat, int ed) {
        this.start = stat;
        this.end = ed;
        temp = ed;
    }

    @Override
    //这里的话计算任务
    //如果任务小于阈值的话就执行
    //如果大于阈值的话就拆分
    protected Integer compute() {
        int sum = 0;
        boolean b = (end - start) > YUZHI;
        //拆分的之任务小于阈值
        if (!b) {
            //这里的话，是分配了具体的任务，我在这里的加法就是因为我  13   35  我加前不加后。但是最后一个可能会被忽略，所以才判断最后的位置加上值
            for (int i = start; i < end; i++) {
                sum += i;
                if (end == temp)
                    sum += temp;
            }
        } else {
            //拆分的任务大于阈值，那么的话接下来就又开始拆分
            ArrayList<CountTask> list = new ArrayList<>();
            for (int i = start; i <= temp; i += YUZHI) {
                CountTask countTask = new CountTask(i, (i + YUZHI) > temp ? temp : (i + YUZHI));
                countTask.fork();
                list.add(countTask);
            }
            for (CountTask countTask : list) {
                Integer join = countTask.join();
                sum += join;
            }
        }
        return sum;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        long l = System.currentTimeMillis();
        CountTask countTask = new CountTask(1, 5);
        ForkJoinTask<Integer> submit = forkJoinPool.submit(countTask);
        System.out.println(System.currentTimeMillis() - l);
        long l1 = System.currentTimeMillis();
        for (int i = 0; i <= 5; i++) {
            sump += i;
        }
        Thread.sleep(1000);
        System.out.println(System.currentTimeMillis() - l1);
        System.out.println(submit.get());
        System.out.println(sump);

    }
}
