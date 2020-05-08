package com.example.redis.mdredis.jedis;

import com.alibaba.fastjson.JSONObject;
import com.example.redis.mdredis.exception.ApplicationException;
import org.apache.tomcat.util.security.MD5Encoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.interceptor.KeyGenerator;

import java.lang.reflect.Method;
import java.security.MessageDigest;

public class CacheKeyGenerator implements KeyGenerator {

	private static final Logger logger = LoggerFactory.getLogger(CacheKeyGenerator.class.getName());

	@Override
	public Object generate(Object o, Method method, Object... params) {
		StringBuilder sb = new StringBuilder();
		sb.append(o.getClass().getName());
		sb.append(".");
		sb.append(method.getName());
		
		sb.append(":");
		if (params != null) {			
			sb.append(JSONObject.toJSONString(params));
		} else {
			sb.append("null");
		}
		
		try {
			byte[] hash = MessageDigest.getInstance("MD5").digest(sb.toString().getBytes("UTF-8"));
			String key = MD5Encoder.encode(hash);
			logger.debug("cache key: {} => {}", sb, key);
			
			return key;
		} catch (Exception e) {
			logger.error("生成Redis Cache Key失败: " + sb, e);
			throw new ApplicationException(e);
		}
	}

}
