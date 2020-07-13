package com.book.bingfa.threadPool;

import java.util.concurrent.*;

/**
 * @Author： Dingpengfei
 * @Description：用来测试futureTask
 * @Date： 2020-7-1 14:16
 */
public class FutureTaskTest {
    private final ConcurrentHashMap<Object, Future<String>> taskCache = new ConcurrentHashMap<>();

    private String exeutionTask(final String taskName) {
        while (true) {
            Future<String> future = taskCache.get(taskName);
            if (future == null) {
                Callable<String> task = () -> {
                    return taskName;
                };
                FutureTask<String> futureTask = new FutureTask<>(task);
                future = taskCache.putIfAbsent(taskName, futureTask); //目前的话认为的就是直接设置值进去
                if (future == null) {
                    future = futureTask;
                    futureTask.run();                   //执行任务
                }
                try {
                    return future.get();
                } catch (Exception e) {
                    taskCache.remove(taskName, future);
                }

            }
        }
    }
}
