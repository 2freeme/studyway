package com.example.redis.rpcutil;

import java.io.Serializable;

/**
 * User: zhaolp
 */
public interface MqMsgProcesser {
	public void process(Serializable eventData, Class resultClass);
}
