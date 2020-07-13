package com.example.redis.rpcutil;


import com.example.redis.rpcutil.exception.SendRefuseException;

import java.io.Serializable;

/**
 * User: zhaolp
 * Date: 2017-06-29 下午5:59
 */
public interface MqMsgTemplate {

	void send(String queueName, String exchangeName, Serializable eventContent) throws SendRefuseException;
		
	void send(String queueName, String exchangeName, Serializable eventContent, CodecFactory codecFactory) throws SendRefuseException;
}
