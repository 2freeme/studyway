package com.example.redis.controller.rediservie.impl;

import com.example.redis.controller.rediservie.RedisLockOperationHolder;
import com.example.redis.controller.rediservie.RedisService;
import com.studyway.redis.test.entity.RedisDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;

import java.util.List;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-4-6 22:47
 */
public class RedisServiceImpl implements RedisService {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;


    @Override
    public void setRedis(RedisDemo redisDemo) {

    }

    @Override
    public void getRedis(RedisDemo redisDemo) {

    }


    public void rollBack(RedisDemo redisDemo){
        redisTemplate.

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

                        for(RedisDemo areaReq : operationList) {
                            try {
                                //归滚重写的步骤
                                areaInvRedisFacade.hincrByLockInv(areaReq.getKey(),AreaInvRedisRequest.SHARING_LOCK_INV,areaReq.getHincValue()*-1);

                            } catch (Exception ex) {
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
