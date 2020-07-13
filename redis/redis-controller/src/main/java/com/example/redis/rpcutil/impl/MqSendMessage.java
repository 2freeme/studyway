//package com.example.redis.rpcutil.impl;
//
//import java.io.Serializable;
//
///**
// * User: zhaolp
// * 在App和RabbitMQ之间转送的消息
// */
//@SuppressWarnings("serial")
//public class MqSendMessage implements Serializable{
//
//	private String queueName;
//
//	private String exchangeName;
//
//	private Serializable eventData;
//
//	private Class clstype=String.class;
//
//	public MqSendMessage(String queueName, String exchangeName, Serializable eventData) {
//		this.queueName = queueName;
//		this.exchangeName = exchangeName;
//		this.eventData = eventData;
//	}
//
//	public MqSendMessage(String queueName, String exchangeName, Serializable eventData, Class clstype) {
//		this.queueName = queueName;
//		this.exchangeName = exchangeName;
//		this.eventData = eventData;
//		this.clstype = clstype;
//	}
//
//	public MqSendMessage() {
//	}
//
//	public String getQueueName() {
//		return queueName;
//	}
//
//	public void setQueueName(String queueName) {
//		this.queueName = queueName;
//	}
//
//	public String getExchangeName() {
//		return exchangeName;
//	}
//
//	public void setExchangeName(String exchangeName) {
//		this.exchangeName = exchangeName;
//	}
//
//	public Serializable getEventData() {
//		return eventData;
//	}
//
//	public void setEventData(Serializable eventData) {
//		this.eventData = eventData;
//	}
//
//	public Class getClstype() {
//		return clstype;
//	}
//
//	public void setClstype(Class clstype) {
//		this.clstype = clstype;
//	}
//
//	@Override
//	public String toString() {
//		return "MqSendMessage {" +
//				"queueName='" + queueName + '\'' +
//				", exchangeName='" + exchangeName + '\'' +
//				", eventData='" +eventData+ '\'' +
//				", clstype='" + clstype.getName() + '\'' +
//				'}';
//	}
//}
