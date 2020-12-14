package com.example.demo.common.transanctionMsg;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.CollectionUtils;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.demo.common.utils.IdGenerator;
import com.example.demo.module.pojo.TTransactionMsg;
import com.example.demo.module.service.TTransactionMsgService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Aspect
@Component
public class TransactionMessageAspect extends TransactionSynchronizationAdapter {
	@Autowired
	TransactionInterceptorHandler transactionInterceptorHandler;
    @Autowired
    private ThreadPoolTaskExecutor threadPoolA;//变量名称为
    
    @Autowired
    private TTransactionMsgService tTransactionMsgService;
    
    /**
     * 入口：拦截带有 @Transactional的方法,标记当前方法已进入事务模式
     * org.springframework.transaction.annotation.Transactional
     */
    @Before("@annotation(org.springframework.transaction.annotation.Transactional)")
    public void registerTransactionSyncrhonization(JoinPoint joinPoint) {
        TransactionSynchronizationManager.registerSynchronization(this);
        String methoName = joinPoint.getSignature().getName();
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        transactionInterceptorHandler.signInTransaction(className,methoName);
        
    }
    

    @Override
    public void beforeCommit(boolean readOnly) {
       log.debug("before commit start TransionMsg");
       List<MessageEntity> messageEntities = transactionInterceptorHandler.getMessagess();
       if( !CollectionUtils.isEmpty(transactionInterceptorHandler.getMessagess())) {
    	   long id =  IdGenerator.genId();
    	   	messageEntities.stream().map(
    	   			e -> {
    		    	   	 TTransactionMsg tmsg = new TTransactionMsg();
    		    	   	 tmsg.setId(e.getId());
    		    	   	 tmsg.setBatchNo(id);
    		        	 tmsg.setBusiClass(transactionInterceptorHandler.getClassName());
    		        	 tmsg.setBusiMethod(transactionInterceptorHandler.getMethodName());
    		        	 tmsg.setMqTopic(e.getTopic());
    		        	 tmsg.setStatus(0);
    		        	 tmsg.setRetryTimes(0);
    		        	 tmsg.setUpdateTime(LocalDateTime.now());
    		        	 tmsg.setCreateTime(LocalDateTime.now());
    		        	 tmsg.setShardingColumn(e.getShardingColumn());
    		        	 tmsg.setMessage(e.getContent());
    		        	 tmsg.setMqTag(e.getTag());
    	    		     return tmsg; }
    	   			).collect(Collectors.toList()).stream().
    	   	    forEach(e->tTransactionMsgService.save(e));
    	   	
    	   	//int i= 1/0;
    	   	//tTransactionMsgService.saveBatch(tTransactionMsglist, 100);
       }
       log.info("before commit end batch save TransionMsg");
    }

    /**
     * 在事务结束并且没被回滚时再依次执行Callable方法
     *
     * @param status
     */
    @Override
    public void afterCompletion(int status) {
    	log.debug("afterCompletion");
        try {
            if (status != STATUS_ROLLED_BACK && !CollectionUtils.isEmpty(transactionInterceptorHandler.getActions())) {
            	List<Callable<?>> callableList = transactionInterceptorHandler.getActions();
            	List<Future<?>> futureList = new ArrayList<>(callableList.size());
            	for (Callable<?> action : callableList) {
                   // action.call();
                	Future<?> callRet =  threadPoolA.submit(action);
                	futureList.add(callRet);
                }
            	
            	for (Future<?> future : futureList) {
            		final MessageEntity msgEntity = (MessageEntity)future.get();
            		threadPoolA.execute(()->{
            			 TTransactionMsg tmsg = new TTransactionMsg();
    		    	   	 //tmsg.setId(msgEntity.getId());
    		    	   	 //tmsg.setShardingColumn(msgEntity.getShardingColumn());
    		        	 tmsg.setStatus(1);
    		        	 tmsg.setUpdateTime(LocalDateTime.now());
            			 //tTransactionMsgService.updateById(tmsg);
    		        	  UpdateWrapper<TTransactionMsg> updateWrapper=new UpdateWrapper<>();
    		        	  updateWrapper.eq("id",msgEntity.getId());
    		        	  updateWrapper.eq("sharding_column", msgEntity.getShardingColumn());
            			 tTransactionMsgService.update(tmsg, updateWrapper);
            		});
                 }
            	
            }
        } catch (Exception e) {
        	  log.info("afterCompletion mq error!",e);
        } finally {
        	transactionInterceptorHandler.clear();
        }

    }

    @Override
    public void afterCommit() {
        log.debug("afterCommit");
    }


    @Override
    public void suspend() {
    	log.debug("suspend");
    }

    @Override
    public void resume() {
    	log.debug("resume");
    }

    @Override
    public void flush() {
    	log.debug("flush");
    }

    @Override
    public void beforeCompletion() {
        log.debug("beforeCompletion");
    }

}