//package com.example.redis.rpcutil;
//
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//
//import java.util.Map;
//
//public interface MqMsgController {
//
//	/**
//	 * 创建并把控制器启动方法
//	 */
//	void start();
//
//	/**
//	 * 控制器停止并销毁方法
//	 */
//	void stop();
//	/**
//	 * 控制器启动方法
//	 */
//	void start(String queueName);
//
//	/**
//	 * 控制器启动方法
//	 */
//	void start(String queueName, Integer maxLength);
//
//	/**
//	 * 获取发送模版
//	 */
//	MqMsgTemplate getEopEventTemplate();
//
//	/**
//	 * 绑定消费程序到对应的exchange和queue
//	 */
//	MqMsgController add(String queueName, String exchangeName, MqMsgProcesser mqMsgProcesser);
//
//	/*in map, the key is queue name, but value is exchange name*/
//	MqMsgController add(Map<String, String> bindings, MqMsgProcesser mqMsgProcesser);
//
//	CachingConnectionFactory getConnectionFactory();
//	/**
//	 * 删除队列
//	 * @param queueName
//	 * @return
//	 */
//	boolean deleteQueue(String queueName);
//	/**
//	 * 删除队列根据队列名称前缀
//	 * @param queueNamePrefix
//	 * @return
//	 */
//	int deleteQueueByPrefix(String queueNamePrefix, String exchangeName);
//	/**
//	 * 清空队列
//	 * @param queueName
//	 * @param noWait
//	 */
//	void purgeQueue(String queueName, boolean noWait);
//
//	/**
//	 * 删除交换器
//	 * @param exchangeName
//	 * @return
//	 */
//	boolean deleteExchange(String exchangeName);
//
//	/**
//	 * 获取已绑定的队列
//	 * @return Map
//	 */
//	public Map<String, Queue> getAllQueues();
//
//	public CachingConnectionFactory initRabbitConnectionFactory();
//
//	public boolean resetMQ();
//
//	public String getCMD();
//
//	public void setCMD(String CMD);
//}
