//package com.example.redis.rpcutil.impl;
//
//import com.alibaba.fastjson.JSON;
//import com.example.redis.rpcutil.CodecFactory;
//import com.example.redis.rpcutil.MqMsgTemplate;
//import com.example.redis.rpcutil.exception.SendRefuseException;
//import com.example.redis.rpcutil.util.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.amqp.AmqpException;
//import org.springframework.amqp.core.AmqpTemplate;
//
//import java.io.Serializable;
//
///**
// * User: zhaolp
// */
//public class DefaultMqMsgTemplate implements MqMsgTemplate {
//
//	private static final Logger logger = LoggerFactory.getLogger(DefaultMqMsgTemplate.class);
//
//	private AmqpTemplate eventAmqpTemplate;
//
//	private CodecFactory defaultCodecFactory;
//
//	private DefaultMqMsgController eec;
//
//	public DefaultMqMsgTemplate(AmqpTemplate eopAmqpTemplate,
//                                CodecFactory defaultCodecFactory, DefaultMqMsgController eec) {
//		this.eventAmqpTemplate = eopAmqpTemplate;
//		this.defaultCodecFactory = defaultCodecFactory;
//		this.eec = eec;
//	}
//
//	public DefaultMqMsgTemplate(AmqpTemplate eopAmqpTemplate, CodecFactory defaultCodecFactory) {
//		this.eventAmqpTemplate = eopAmqpTemplate;
//		this.defaultCodecFactory = defaultCodecFactory;
//	}
//
//	@Override
//	public void send(String queueName, String exchangeName, Serializable eventContent)
//			throws SendRefuseException {
//		this.send(queueName, exchangeName, eventContent, defaultCodecFactory);
//	}
//
//	@Override
//	public void send(String queueName, String exchangeName, Serializable eventContent,
//			CodecFactory codecFactory) throws SendRefuseException {
//		if (StringUtils.isEmpty(queueName) || StringUtils.isEmpty(exchangeName)) {
//			throw new SendRefuseException("queueName exchangeName can not be empty.");
//		}
//
//		if (!eec.beBinded(exchangeName, queueName))
//			eec.declareBinding(exchangeName, queueName); //发送消息时，判断是否有绑定关系在binded，如果没有则声明并添加到binded
//
//		// 构造成Message
//		MqSendMessage msg = new MqSendMessage(queueName, exchangeName,eventContent,eventContent.getClass());
//
//		try {
//			eventAmqpTemplate.convertAndSend(exchangeName, queueName, JSON.toJSONString(msg));
//		} catch (AmqpException e) {
//			logger.error("send event fail. Event Message : [" + eventContent + "]", e);
//			throw new SendRefuseException("send event fail", e);
//		}
//	}
//}
