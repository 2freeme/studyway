package com.example.redis.lock.reentrant.redislock;

import com.studyway.redis.test.entity.RedisDemo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *描述：redis操作ccs销售层锁定库存时的，回滚对象
 */
public class RedisLockOperationHolder {
	private static final ThreadLocal<List<RedisDemo>> redisOperationHolder = new ThreadLocal<List<RedisDemo>>();
	private static final ThreadLocal<Boolean> limitControl = new ThreadLocal<Boolean>();

	/**
	 * 操作锁定库存成功后，把对应的operation添加到list，以便回滚操作
	 */
	public static void putRedisOperation(RedisDemo areaReq) {
		if (redisOperationHolder.get() == null) {
			redisOperationHolder.set(new ArrayList<RedisDemo>());
		}		
		redisOperationHolder.get().add(areaReq);
	}
	
	/**
	 * 获取逆向operation list用于回滚操作
	 * 可能返回空值
	 */
	public static List<RedisDemo> getReverseOperationList() {
		List<RedisDemo> newList = null;
		List<RedisDemo> list = redisOperationHolder.get();
		if(list != null) {
			newList = new ArrayList<RedisDemo>(list);
			Collections.reverse(newList);
		}		
		return newList;
	}
	
	/**
	 * 清空threadlocal
	 */
	public static void clearRedisOperation() {
		if (redisOperationHolder.get() != null) {
			redisOperationHolder.get().clear();
			redisOperationHolder.remove();// remove之后会重新初始化
			////logger.debug("已清除 ThreadLocal RedisLockInvOperationHolder");
		}
		if (limitControl != null) {
			limitControl.remove();
			//logger.debug("已清除 ThreadLocal RedisLockInvOperationHolder");
		}
	}
	public static Boolean getLimitControl() {
		return limitControl.get();
	}
	
	public static void setLimitControl(Boolean value) {
		limitControl.set(value);
	}
	
	
	

	
}
