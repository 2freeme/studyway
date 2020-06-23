package com.book.bingfa.threadPool;

import org.apache.log4j.Logger;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池的拒绝策略
 * 这里的话，就是再增加60s的等待的时间。（加入死循环来看）
 * 
 * <pre>
 * 由于超出线程范围(maximumPoolSize)和队列容量(BlockingQueue)而使执行被中断时所使用的处理器
 * 其效果是，当最大线程数和队列数都满了，则本处理器会尝试等待一段时间（waitTimeout），期间只要有可用线程队列缓冲，则继续执行任务，而不会抛出异常中断
 * 使用本处理器后，queueCapacity的参数大小已不产生影响，只要大于0即可
 * waitTimeout值应该根据任务task的平均执行时间值再稍微偏大一点
 * </pre>
 * 
 * @version 1.00.00
 * 
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public class BlockRejectedExecutionHandler implements RejectedExecutionHandler {
    
    private final Logger logger=null ;

    /**
     * waitTimeout值应该根据任务task的平均执行时间值再稍微偏大一点
     */
    private int waitTimeout = 60; // 等待可用线程超时时间，默认60秒
    
    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        long start = System.currentTimeMillis();
        int elapsedTime = 0;
        
        while(true) {            
            if (elapsedTime <= waitTimeout) {
                int rc = executor.getQueue().remainingCapacity();
                
                if (rc > 0) { // 有空闲线程
                    if(elapsedTime > 0) logger.debug("等待"+ elapsedTime +"秒后线程队列缓冲可用数:"+ rc);
                    executor.execute(r); //执行当前等待的任务（加入queue）
                    break; // 不中断继续处理后面的任务
                } else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        logger.error(e.getMessage(), e);
                        Thread.currentThread().interrupt();
                        throw new RuntimeException(e.getMessage(), e);
                    }
                }
                
                long end = System.currentTimeMillis();
                elapsedTime = (int)(end - start) / 1000;                
            } else {
                throw new RejectedExecutionException("等待可用线程超时(" + elapsedTime + " sec), 任务("+r.toString()+")被拒绝执行");
            }
            
        }
    }

    public void setWaitTimeout(int waitTimeout) {
        this.waitTimeout = waitTimeout;
    }
    
}
