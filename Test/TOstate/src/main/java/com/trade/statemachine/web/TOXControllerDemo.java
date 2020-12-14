package com.trade.statemachine.web;
import com.trade.statemachine.to.TradeOrderMachineBuilder;
import com.trade.statemachine.tox.TradeOrderXBuilder;
import com.trade.statemachine.tox.TradeOrderXEvents;
import com.trade.statemachine.tox.TradeOrderXStates;
import com.trade.statemachine.web.entity.TradeOrderX;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/statemachine/tox")
public class TOXControllerDemo {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private TradeOrderXBuilder tradeOrderXBuilder;
	@Resource(name = "stateMachineRedisPersister")
	private StateMachinePersister stateMachineRedisPersister;
	@Resource(name = "tradeOrderXPersister")
	private StateMachinePersister tradeOrderXPersister;

	@RequestMapping("/createOrder")
	public void createOrder(String orderId) throws Exception {
		StateMachine<TradeOrderXStates, TradeOrderXEvents> stateMachine = tradeOrderXBuilder.build();
		TradeOrderX order = new TradeOrderX(orderId);
		order.setState(TradeOrderXStates.CREATE.name());
		tradeOrderXPersister.restore(stateMachine,order);
		stateMachine.sendEvent(TradeOrderXEvents.BUSINESS_CANCELLED);
		stateMachineRedisPersister.persist(stateMachine,order.getId());
		logger.info("最终状态：" + stateMachine.getState().getId());
	}
	/**
	 * 客户付款
	 * @param orderId
	 * @param isGroup
	 * @throws Exception
	 */
	@RequestMapping("/toPay")
	public void toPay(String orderId,boolean isGroup) throws Exception {
		StateMachine<TradeOrderXStates, TradeOrderXEvents> stateMachine = tradeOrderXBuilder.build();
		TradeOrderX order = new TradeOrderX(orderId, isGroup, TradeOrderXStates.WAIT_FOR_PAY.name());
		tradeOrderXPersister.restore(stateMachine,order);
		Message<TradeOrderXEvents> message = MessageBuilder.withPayload(TradeOrderXEvents.CUSTOMER_COMPLEMENT_LINE).setHeader("order", order).build();
		stateMachine.sendEvent(message);
		logger.info("最终状态：" + stateMachine.getState().getId());
	}
	@RequestMapping("/toPay2")
	public void toPay2(String orderId,boolean isGroup) throws Exception {
		StateMachine<TradeOrderXStates, TradeOrderXEvents> stateMachine = tradeOrderXBuilder.build();
		TradeOrderX order = new TradeOrderX(orderId, isGroup, TradeOrderXStates.WAIT_FOR_PAY.name());
		tradeOrderXPersister.restore(stateMachine,order);
		Message<TradeOrderXEvents> message = MessageBuilder.withPayload(TradeOrderXEvents.CUSTOMER_COMPLEMENT_LINE).setHeader("order", order).build();
		stateMachine.sendEvent(message);
		logger.info("最终状态：" + stateMachine.getState().getId());
	}
	/**
	 * Fay 存储状态机
	 * 保存状态机至Redis
	 * @param id
	 * @throws Exception
	 */
	@RequestMapping("/saveRedis")
	public void saveRedis(String id,String state) throws Exception {
		StateMachine<TradeOrderXStates, TradeOrderXEvents> stateMachine = tradeOrderXBuilder.build();
		stateMachine.start();
		TradeOrderX order = new TradeOrderX();
		order.setId(id);
		order.setState(state);
		tradeOrderXPersister.restore(stateMachine,order);
		//发送PAY事件
		Message<TradeOrderXEvents> message = MessageBuilder.withPayload(TradeOrderXEvents.AUDIT_BY_BUSINESS).setHeader("order", order).build();
		stateMachine.sendEvent(message);
		//持久化stateMachine
		stateMachineRedisPersister.persist(stateMachine, order.getId());
		logger.info("状态机ID：" + "stateMachineID: "+ id+" 存储时状态为"+stateMachine.getState().getId());
	}
	/**
	 * 从Redis恢复状态机
	 * @param id
	 * @throws Exception
	 */
	@RequestMapping("/restoresaveRedis")
	public void restoresaveRedis(String id) throws Exception {
		StateMachine<TradeOrderXStates, TradeOrderXEvents> stateMachine = tradeOrderXBuilder.build();
		stateMachineRedisPersister.restore(stateMachine, id);
		logger.info("状态机ID：" + "stateMachineID: "+ id+" 恢复时状态为"+stateMachine.getState().getId());
	}
}
