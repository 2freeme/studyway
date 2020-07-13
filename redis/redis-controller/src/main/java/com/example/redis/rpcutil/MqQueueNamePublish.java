package com.example.redis.rpcutil;

/**
 * Created by zhaolp on 2017/7/6 0006.
 * 发布队列名称到服务器
 */
public interface MqQueueNamePublish {
	
    void publishAddNewQueue(String queueName);
    
    void publishStartAllListeners();
    
    void publishStopAllListeners();
    
}
