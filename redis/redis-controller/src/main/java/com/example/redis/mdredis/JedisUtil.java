package com.example.redis.mdredis;


import com.example.redis.mdredis.exception.ApplicationException;
import com.example.redis.mdredis.jedis.JedisCallback;
import com.example.redis.mdredis.jedis.PipelineCallback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPipeline;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.*;

/**
 * 
 */
@Service
public class JedisUtil {
	
	private static int RETRY_NUM = 5;
	
	@Autowired
	private ShardedJedisPool sharedJedisPool;

	public void setSharedJedisPool(ShardedJedisPool sharedJedisPool) {
		this.sharedJedisPool = sharedJedisPool;
	}
	/**
	 * 获取ShardedJedis池资源
	 * @return
	 */
	public ShardedJedis getJedis(){
		return sharedJedisPool.getResource();
	}
	/**
	 * 返回ShardedJedis池资源
	 * @param jedis
	 */
	public void returnJedisResource(ShardedJedis shardedJedis) {
		if(shardedJedis == null){
			return;
		}
		shardedJedis.close();
		//sharedJedisPool.returnResource(shardedJedis);
	}
	
	public void returnJedisBrokenResource(ShardedJedis shardedJedis){
		if(shardedJedis == null){
			return;
		}
		shardedJedis.close();
		//sharedJedisPool.returnBrokenResource(shardedJedis);
	}
	
	/**
	 * 
	* @Title: openPipeline 
	* @Description: jedis管道批量操作  支持异常情况下的5次补偿操作
	* @param @param callback    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	public void execJedisPipelineOperate(PipelineCallback callback){
		for(int index = 1; index <= RETRY_NUM ; index++){
			try{
				invokePipeline(callback);
			}catch(ApplicationException ex){
				if(index < RETRY_NUM){
					continue;
				}
				throw ex;
			}
			break;
		}
	}
	
	/**
	 * 
	* @Title: execJedisOperate 
	* @Description: jedis操作  支持异常情况下的5次补偿操作
	* @param @param callback
	* @param @return
	* @return T    返回类型 
	* @throws
	 */
	public <T> T execJedisOperate(JedisCallback<T> callback){
		T result = null;
		for(int index = 1; index <= RETRY_NUM ; index++){
			try{
				result = invokeJedis(callback);
			}catch(ApplicationException ex){
				if(index < RETRY_NUM){
					continue;
				}
				throw ex;
			}
			break;
		}
		return result;
	}
	
	/**
	 * 
	* @Title: invokePipeline 
	* @Description: jedis管道操作核心方法
	* @param @param callback
	* @return void    返回类型 
	* @throws
	 */
	private void invokePipeline(PipelineCallback callback){
		boolean isReturn = true;
		ShardedJedis shardedJedis = null;
		try{
			shardedJedis = getJedis();
			ShardedJedisPipeline pipeline = shardedJedis.pipelined();
			callback.invokePipeline(pipeline);
			pipeline.sync();
		}catch (JedisConnectionException e){
            isReturn = false;
            returnJedisBrokenResource(shardedJedis);
            throw new ApplicationException(e.getMessage());
        } catch(Exception ex){
			throw new ApplicationException(ex.getMessage());
		}finally{
			if(isReturn){
				returnJedisResource(shardedJedis);
			}
		}
	}


	/**
	 *
	 * @Title: invokePipeline
	 * @Description: jedis管道操作核心方法
	 * @param @param callback
	 * @return void    返回类型
	 * @throws
	 */
	private List<Object> invokePipelineWithResponse(PipelineCallback callback){
		boolean isReturn = true;
		ShardedJedis shardedJedis = null;
		List<Object> results = null;
		try{
			shardedJedis = getJedis();
			ShardedJedisPipeline pipeline = shardedJedis.pipelined();
			callback.invokePipeline(pipeline);
			results = pipeline.syncAndReturnAll();
		}catch (JedisConnectionException e){
            isReturn = false;
            returnJedisBrokenResource(shardedJedis);
            throw new ApplicationException(e.getMessage());
        }
		catch(Exception ex){
		/*	if(ex instanceof JedisConnectionException){
				isReturn = false;
				returnJedisBrokenResource(shardedJedis);
			}*/
			throw new ApplicationException(ex.getMessage());
		}finally{
			if(isReturn){
				returnJedisResource(shardedJedis);
			}
		}
		return results;
	}
	
	/**
	 * 
	* @Title: invokeJedis 
	* @Description: jedis操作核心方法
	* @param @param callback
	* @param @return
	* @return T    返回类型 
	* @throws
	 */
	private <T> T invokeJedis(JedisCallback<T> callback){
		boolean isReturn = true;
		ShardedJedis shardedJedis = null;
		try{
			shardedJedis = getJedis();
			return callback.invoke(shardedJedis);
		}catch (JedisConnectionException e){
            isReturn = false;
            returnJedisBrokenResource(shardedJedis);
            throw new ApplicationException(e.getMessage());
        }
		catch(Exception ex){
		/*	if(ex instanceof JedisConnectionException){
				isReturn = false;
				returnJedisBrokenResource(shardedJedis);
			}*/
			throw new ApplicationException(ex.getMessage());
		}finally{
			if(isReturn){
				returnJedisResource(shardedJedis);
			}
		}
	}
	
	/**
	 * 
	* @Title: executeIncrValueToCache 
	* @Description: 得到从缓冲中自增num的值
	* @param @param pkKey
	* @param @param pkNum
	* @param @return
	* @return Long    返回类型 
	* @throws
	 */
	public Long execIncrByToCache(final String cacheKey,final int num){
		Long pkVal = execJedisOperate(new JedisCallback<Long>() {
			@Override
			public Long invoke(ShardedJedis jedis) {
				return jedis.incrBy(cacheKey, (long)num);
			}
		});
		return pkVal;
	}
	
	/**
	 * 
	* @Title: execIncrToCache 
	* @Description: 得到从缓冲中自增1的值
	* @param @param cacheKey
	* @param @return
	* @return Long    返回类型 
	* @throws
	 */
	public Long execIncrToCache(final String cacheKey){
		Long pkVal = execJedisOperate(new JedisCallback<Long>() {
			@Override
			public Long invoke(ShardedJedis jedis) {
				return jedis.incr(cacheKey);
			}
		});
		return pkVal;
	}
	
	/**
	 * 
	* @Title: execDecrByToCache 
	* @Description: 
	* @param @param cacheKey
	* @param @param num
	* @param @return
	* @return Long    返回类型 
	* @throws
	 */
	public Long execDecrByToCache(final String cacheKey,final int num){
		Long pkVal = execJedisOperate(new JedisCallback<Long>() {
			@Override
			public Long invoke(ShardedJedis jedis) {
				return jedis.decrBy(cacheKey, (long)num);
			}
		});
		return pkVal;
	}
	
	/**
	 * 
	* @Title: execDelToCache 
	* @Description: 删除缓存
	* @param @param cacheKey
	* @param @return
	* @return boolean    返回类型 
	* @throws
	 */
	public boolean execDelToCache(final String cacheKey){
		return execJedisOperate(new JedisCallback<Boolean>() {
			@Override
			public Boolean invoke(ShardedJedis jedis) {
				return jedis.del(cacheKey)==
						0?false:true;
			}
		});
	}
	
	/**
	 * 
	* @Title: execSetToCache 
	* @Description: 存入缓存值 LRU
	* @param @param cacheKey
	* @param @param value
	* @return void  返回类型 
	* @throws
	 */
	public void execSetToCache(final String cacheKey,final String value){
		execJedisOperate(new JedisCallback<Void>() {
			@Override
			public Void invoke(ShardedJedis jedis) {
				jedis.set(cacheKey, value);
				return null;
			}
		});
	}
	
	/**
	 * 
	* @Title: execSetexToCache 
	* @Description: 执行
	* @param @param key
	* @param @param seconds
	* @param @param value
	* @return void    返回类型 
	* @throws
	 */
	public void execSetexToCache(final String cacheKey,final int seconds,final String value){
		execJedisOperate(new JedisCallback<Void>() {
			@Override
			public Void invoke(ShardedJedis jedis) {
				jedis.setex(cacheKey, seconds,value);
				return null;
			}
		});
	}
	
	/**
	 * 
	* @Title: execGetFromCache 
	* @Description: 
	* @param @param cacheKey
	* @param @return
	* @return String    返回类型 
	* @throws
	 */
	public String execGetFromCache(final String cacheKey){
		return execJedisOperate(new JedisCallback<String>(){
			@Override
			public String invoke(ShardedJedis jedis) {
				return jedis.get(cacheKey);
			}
		});
	}
	
	/**
	 * 
	* @Title: execExistsFromCache 
	* @Description: 是否已经缓存
	* @param @param cacheKey
	* @param @return
	* @return Boolean    返回类型 
	* @throws
	 */
	public Boolean execExistsFromCache(final String cacheKey){
		return execJedisOperate(new JedisCallback<Boolean>() {
			@Override
			public Boolean invoke(ShardedJedis jedis) {
				return jedis.exists(cacheKey);
			}
		});
	}
	
	/**
	 * 
	* @Title: execExpireToCache 
	* @Description: 设置过期时间
	* @param @param cacheKey
	* @param @return
	* @return Boolean    返回类型 
	* @throws
	 */
	public Long execExpireToCache(final String cacheKey,final int seconds){
		return execJedisOperate(new JedisCallback<Long>() {
			@Override
			public Long invoke(ShardedJedis jedis) {
				return jedis.expire(cacheKey, seconds);
			}
		});
	}
	
	/**
	 * 
	* @Title: execSetnxToCache 
	* @Description: 
	* @param @param cacheKey
	* @param @param value
	* @param @return
	* @return Long    返回类型 
	* @throws
	 */
	public Long execSetnxToCache(final String cacheKey,final String value){
		return execJedisOperate(new JedisCallback<Long>(){
			@Override
			public Long invoke(ShardedJedis jedis) {
				return jedis.setnx(cacheKey, value);
			}});
	}

	/**
	 * 原子操作 设置key同时指定失效时间
	 * @Title: execSetnxToCache
	 * @Description:
	 * @param @param cacheKey
	 * @param @param value
	 * @param @return
	 * @return Long    返回类型
	 * @throws
	 */
	public String execSetexExpireTime(final String cacheKey,final int seconds,final String value){
		return execJedisOperate(new JedisCallback<String>(){
			@Override
			public String invoke(ShardedJedis jedis) {
				return jedis.setex(cacheKey,seconds,value);
			}});
	}

	/**
	 * 
	* @Title: batchSetToCache 
	* @Description: 使用管道批量插入键值
	* @param  map
	* @return void  返回类型 
	* @throws
	 */
	public void batchSetToCache(final Map<String,String> map){
		execJedisPipelineOperate(new PipelineCallback() {
			@Override
			public void invokePipeline(ShardedJedisPipeline pipeline) {
				if(map==null||map.size()==0){
					return;
				}
				for (Map.Entry<String, String> entry : map.entrySet()) {
				      pipeline.set(entry.getKey(), entry.getValue());
				}
			}
		});
	}

	/**
	 *
	 * @Title: batchSetToCache
	 * @Description: 使用管道批量插入键值并指定失效时间
	 * @param  map
	 * @return void  返回类型
	 * @throws
	 */
	public void batchSetExpireToCache(final Map<String,String> map,final int seconds){
		execJedisPipelineOperate(new PipelineCallback() {
			@Override
			public void invokePipeline(ShardedJedisPipeline pipeline) {
				if(map==null||map.size()==0){
					return;
				}
				for (Map.Entry<String, String> entry : map.entrySet()) {
					pipeline.setex(entry.getKey(), seconds,entry.getValue());
				}
			}
		});
	}
	/**
	 * 
	* @Title: batchDeleteCache 
	* @Description: 使用管道批量删除键值
	* @param  map
	* @return void  返回类型 
	* @throws
	 */
	public void batchDeleteCache(final List<String> keyList){
		execJedisPipelineOperate(new PipelineCallback() {
			@Override
			public void invokePipeline(ShardedJedisPipeline pipeline) {
				if(keyList==null||keyList.size()==0){
					return;
				}
				int size = keyList.size();
				for(int i=0;i<size;i++){
				      pipeline.del(keyList.get(i));
				}
			}
		});
	}

	public List<Object> pinelineBatchGet(final List<String> keyList){
		return invokePipelineWithResponse(new PipelineCallback() {
			@Override
			public void invokePipeline(ShardedJedisPipeline pipeline) {
				if(keyList==null||keyList.size()==0){
					return;
				}
				for(String key : keyList){
					pipeline.get(key);
				}
			}
		});
	}


	/**
	 *
	 * @Title: execGetKeysFromCache
	 * @Description:
	 * @param @param keyPatern
	 * @param @return
	 * @return Set<String>    返回类型
	 * @throws
	 */
	public List<String> execGetKeysFromCache(final String keyPatern){
		return execJedisOperate(new JedisCallback<List<String>>() {
			@Override
			public List<String> invoke(ShardedJedis shardedJedis) {
				Collection<Jedis> allShards = shardedJedis.getAllShards();
				ArrayList<String> keys = new ArrayList<>();
				for (Jedis jedis : allShards) {
					final Set<String> k = jedis.keys(keyPatern);
					keys.addAll(k);
				}
				return keys;
			}
		});


	}

	public Long decrBy(final String key, final Long decrement) {
		return execJedisOperate(new JedisCallback<Long>(){
			@Override
			public Long invoke(ShardedJedis jedis) {
				return jedis.decrBy(key, decrement);
			}});
	}

	public Long incrBy(final String key, final Long increment) {
		return execJedisOperate(new JedisCallback<Long>(){
			@Override
			public Long invoke(ShardedJedis jedis) {
				return jedis.incrBy(key, increment);
			}});
	}

	public String hget(String hkey, String field) {
		ShardedJedis resource = sharedJedisPool.getResource();
		String hget = resource.hget(hkey, field);
		resource.close();
		return hget;
	}

	public Long hset(String hkey, String field, String value) {
		ShardedJedis resource = sharedJedisPool.getResource();
		Long hset = resource.hset(hkey, field, value);
		resource.close();
		return hset;
	}

	public Long hincrBy(String hkey, String field, Long value) {
		ShardedJedis resource = sharedJedisPool.getResource();
		Long hincrBy = resource.hincrBy(hkey, field, value);
		resource.close();
		return hincrBy;
	}

}
