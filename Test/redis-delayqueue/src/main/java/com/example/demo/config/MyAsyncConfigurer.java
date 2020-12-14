package com.example.demo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;



@Component
public class MyAsyncConfigurer implements AsyncConfigurer {

    private static final Logger log = LoggerFactory.getLogger(MyAsyncConfigurer.class);

    @Override
    @Bean
    public ThreadPoolTaskExecutor getAsyncExecutor() {
        ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
        //设置核心线程数，默认为1
        threadPool.setCorePoolSize(8);
        // 当核心线程都在跑任务，还有多余的任务会存到此处。
        threadPool.setQueueCapacity(100);
        //最大线程数，默认为Integer.MAX_VALUE，如果queueCapacity存满了，还有任务就会启动更多的线程，直到线程数达到maxPoolSize。如果还有任务，则根据拒绝策略进行处理。
        threadPool.setMaxPoolSize(16);
        // 设置线程活跃时间（秒）
        threadPool.setKeepAliveSeconds(120);
        threadPool.setWaitForTasksToCompleteOnShutdown(true);
        threadPool.setAwaitTerminationSeconds(60 * 15);
        threadPool.setThreadNamePrefix("MyAsync-");
        threadPool.initialize();
        return threadPool;
    }
}
