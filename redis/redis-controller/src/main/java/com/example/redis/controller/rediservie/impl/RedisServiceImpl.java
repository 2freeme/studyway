package com.example.redis.controller.rediservie.impl;

import com.example.redis.controller.rediservie.RedisLockOperationHolder;
import com.example.redis.controller.rediservie.RedisService;
import com.studyway.redis.test.entity.RedisDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-4-6 22:47
 */
@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    @Override
    public void setRedis(RedisDemo redisDemo) {
        rollbackControl();
        redisTemplate.opsForValue().set(redisDemo.getKey(),redisDemo.getValue());
        RedisDemo redisDemo2 =new RedisDemo("dingpf","2222222");
        RedisLockOperationHolder.putRedisOperation(redisDemo2);
    }

    @Override
    public void getRedis(RedisDemo redisDemo) {
        redisTemplate.opsForValue().get(redisDemo.getKey());
    }


    public void rollBack(RedisDemo redisDemo){
        redisTemplate.opsForValue().set(redisDemo.getKey(),"huigunle");

    }

    private void rollbackControl(){
        System.out.println("=======异常事务开始======");
        TransactionSynchronizationManager.registerSynchronization(new RedisLockInvSynchronizationAdapter());
        //在当前线程内创建  一个ThreadLocal
        RedisLockOperationHolder.setLimitControl(true);
    }

    /**
     * 继承spring源码接口,捕捉事务状态,根据事务状态
     *
     *
     */
    public class RedisLockInvSynchronizationAdapter extends TransactionSynchronizationAdapter {


        @Override
        public void afterCompletion(int status) {
            try {
                if (TransactionSynchronization.STATUS_ROLLED_BACK == status || TransactionSynchronization.STATUS_UNKNOWN == status) { //回滚或未知

                    List<RedisDemo> operationList = RedisLockOperationHolder.getReverseOperationList();
                    if (operationList != null) {
                        //logger.debug("事务回滚, 开始回滚redis操作锁定库存的动作");

                        for(RedisDemo redisDemo : operationList) {
                            try {
                                System.out.println("回滚操作中的redis的值  "+redisDemo.toString());
                                //归滚重写的步骤
                               rollBack(redisDemo);

                            } catch (Exception ex) {
                                System.out.println("redis回滚发生错误");
                                //logger.error("回滚redis操作锁定库存发生异常" + areaReq.getKey(), ex);
                            }
                        }
                    }
                }

            } finally {
                //清空当前线程内的ThreadLocal
                RedisLockOperationHolder.clearRedisOperation();
            }
        }

    }
}
