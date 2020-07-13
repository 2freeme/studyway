//package com.example.redis.rpcutil.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.example.redis.rpcutil.CodecFactory;
//import com.example.redis.rpcutil.MqMsgProcesser;
//import com.example.redis.rpcutil.util.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.io.IOException;
//import java.io.Serializable;
//import java.util.Set;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ConcurrentMap;
//
///**
// * MessageListenerAdapter的Pojo
// * <p>消息处理适配器，主要功能：</p>
// * <p>1、将不同的消息类型绑定到对应的处理器并缓存，如将queue+exchange的消息统一交由A处理器来出来</p>
// * <p>2、执行消息的消费分发，调用相应的处理器来消费属于它的消息</p>
// *
// * User: zhaolp
// *
// */
//public class MessageAdapterHandler {
//	private static final Logger logger = LoggerFactory.getLogger(MessageAdapterHandler.class);
//
//	private ConcurrentMap<String, EventProcessorWrap> epwMap;
//
//	public MessageAdapterHandler() {
//		this.epwMap = new ConcurrentHashMap<String, EventProcessorWrap>();
//	}
//
//	public void handleMessage(Object msg) {
//		try {
//			logger.info("Receive an MqSendMessage Object: [" + msg + "]");
//			//if(msg!=null && msg.getClass().getName().equals(String.class.getName())){
//			if(msg!=null && String.class.isAssignableFrom(msg.getClass())){
//				MqSendMessage eem= JSON.parseObject(msg.toString(),MqSendMessage.class);
//				handleMessage(eem);
//			}
//		} catch (Exception ex) {
//			logger.error("处理消息失败", ex);
//		}
//	}
//
//	public void handleMessage(MqSendMessage eem) {
//		logger.debug("Receive an MqSendMessage with handle: [" + eem + "]");
//		// 先要判断接收到的message是否是空的，在某些异常情况下，会产生空值
//		if (eem == null) {
//			logger.warn("Receive an null MqSendMessage, it may product some errors, and processing message is canceled.");
//			return;
//		}
//		if (StringUtils.isEmpty(eem.getQueueName()) || StringUtils.isEmpty(eem.getExchangeName())) {
//			logger.warn("The MqSendMessage's queueName and exchangeName is empty, this is not allowed, and processing message is canceled.");
//			return;
//		}
//		// 解码，并交给对应的EventHandle执行
//		EventProcessorWrap eepw = epwMap.get(eem.getExchangeName() +"|"+ eem.getQueueName());
//		if (eepw == null) {
//			logger.warn("Receive an MqSendMessage, may be no one processor can do it.");
//			//手动注释
//			// eepw=new EventProcessorWrap(new MqMsgDefaultProcessor());
//			epwMap.putIfAbsent(eem.getExchangeName() +"|"+ eem.getQueueName(), eepw); //fixed bug by zhongnh，有可能没有add就直接调handleMessage，这时epwMap为空，需要此处putIfAbsent
//		}
//		try {
//			//logger.debug("Receive an MqSendMessage start process...");
//			eepw.process(eem.getClstype(),eem.getEventData());
//			//logger.debug("MqSendMessage processe end!!!!!");
//		} catch (IOException e) {
//			logger.error("Event content can not be Deserialized, check the provided CodecFactory.",e);
//			return;
//		}
//	}
//
//	protected void add(String queueName, String exchangeName, MqMsgProcesser processor, CodecFactory codecFactory) {
//		if (StringUtils.isEmpty(queueName) || StringUtils.isEmpty(exchangeName) || processor == null || codecFactory == null) {
//			throw new RuntimeException("queueName and exchangeName can not be empty,and processor or codecFactory can not be null. ");
//		}
//		EventProcessorWrap epw = new EventProcessorWrap(processor);
//		EventProcessorWrap oldProcessorWrap = epwMap.putIfAbsent(exchangeName + "|" + queueName, epw);
//		if (oldProcessorWrap != null) {
//			logger.warn("The processor of this queue ["+queueName+"] and exchange ["+exchangeName+"] exists, and the new one can't be add");
//		}
//	}
//
//	/**
//	 *    调用 DynamicQueuesService.createQueue 把新的绑定关系添加到epwMap，包括queue和exchange
//	 * @return
//	 */
//	protected Set<String> getAllBinding() {
//		Set<String> keySet = epwMap.keySet();
//		return keySet;
//	}
//
//	protected static class EventProcessorWrap {
//		private MqMsgProcesser eep;
//		protected EventProcessorWrap(MqMsgProcesser eep) {
//			this.eep = eep;
//		}
//
//		public void process(Class resultClass,Serializable eventData) throws IOException{
//			if(eventData!=null){
//				logger.warn("Start processor massage data :"+eventData);
//			}
//			eep.process(eventData,resultClass);
//		}
//	}
//}
