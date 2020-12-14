package com.trade.statemachine.web;

import javax.annotation.Resource;

import com.trade.statemachine.to.TradeOrderMachineBuilder;
import com.trade.statemachine.web.entity.TradeOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.trade.statemachine.to.TradeOrderEvents;
import com.trade.statemachine.to.TradeOrderStates;

@RestController
@RequestMapping("/statemachine/to")
public class TOControllerDemo {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private TradeOrderMachineBuilder toStateMachineBuilder;
	@Resource(name = "stateMachineRedisPersister")
	private StateMachinePersister stateMachineRedisPersister;
	@Resource(name = "toStateMachinePersister")
	private StateMachinePersister toStateMachinePersister;
	/**
	 * 客户提单
	 * @param orderId
	 * @throws Exception
	 */
	@RequestMapping("/createOrder")
	public void createOrder(String orderId) throws Exception {
		//获取TO状态机
		StateMachine<TradeOrderStates, TradeOrderEvents> stateMachine = toStateMachineBuilder.build();
		//模拟构建TO单
		TradeOrder order = new TradeOrder(orderId);
		order.setState(TradeOrderStates.CREATE.name());
		//用TO单恢复状态机状态.
		toStateMachinePersister.restore(stateMachine,order);
		//创建订单后,发送客户提单事件. 触发TradeOrderAction的CUSTOMER_SUBMIT方法.
		stateMachine.sendEvent(TradeOrderEvents.CUSTOMER_SUBMIT);
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
		StateMachine<TradeOrderStates, TradeOrderEvents> stateMachine = toStateMachineBuilder.build();
		TradeOrder order = new TradeOrder(orderId, isGroup, TradeOrderStates.WAIT_FOR_PAY.name());
		toStateMachinePersister.restore(stateMachine,order);
		Message<TradeOrderEvents> message = MessageBuilder.withPayload(TradeOrderEvents.CUSTOMER_PAY).setHeader("order", order).build();
		stateMachine.sendEvent(message);
		logger.info("最终状态：" + stateMachine.getState().getId());
	}
	/**
	 * 订单取消
	 * @param orderId
	 * @throws Exception
	 */
	@RequestMapping("/orderCancel")
	public void orderCancel(String orderId) throws Exception {
		StateMachine<TradeOrderStates, TradeOrderEvents> stateMachine = toStateMachineBuilder.build();
		TradeOrder order = new TradeOrder(orderId, true, TradeOrderStates.WAIT_FOR_PAY.name());
		toStateMachinePersister.restore(stateMachine,order);
		Message<TradeOrderEvents> message = MessageBuilder.withPayload(TradeOrderEvents.CUSTOMER_CANCEL).setHeader("orderId", order).build();
		stateMachine.sendEvent(message);
		logger.info("最终状态：" + stateMachine.getState().getId());
	}
	/**
	 * 不成团
	 * @param orderId
	 * @throws Exception
	 */
	@RequestMapping("/cantGroup")
	public void cantGroup(String orderId) throws Exception {
		StateMachine<TradeOrderStates, TradeOrderEvents> stateMachine = toStateMachineBuilder.build();
		toStateMachinePersister.restore(stateMachine,orderId);
		Message<TradeOrderEvents> message = MessageBuilder.withPayload(TradeOrderEvents.GROUP_FAILD).build();
		stateMachine.sendEvent(message);
		logger.info("最终状态：" + stateMachine.getState().getId());
	}
	/**
	 * 成团
	 * @param orderId
	 * @throws Exception
	 */
	@RequestMapping("/successGroup")
	public void successGroup(String orderId) throws Exception {
		StateMachine<TradeOrderStates, TradeOrderEvents> stateMachine = toStateMachineBuilder.build();
		TradeOrder order = new TradeOrder(orderId, true, TradeOrderStates.WAIT_FOR_GROUP.name());
		toStateMachinePersister.restore(stateMachine,order);
		Message<TradeOrderEvents> message = MessageBuilder.withPayload(TradeOrderEvents.GROUP_SUCCES).build();
		stateMachine.sendEvent(message);
		logger.info("最终状态：" + stateMachine.getState().getId());
	}
	/**
	 * 保存状态机到内存,不适合分布式.
	 * @param id
	 * @throws Exception
	 */
	@RequestMapping("/savePersister")
	public void tesMemorytPersister(String id) throws Exception {
		StateMachine<TradeOrderStates, TradeOrderEvents> stateMachine = toStateMachineBuilder.build();
		stateMachine.start();
		//发送PAY事件
		stateMachine.sendEvent(TradeOrderEvents.PAY_ALL);
		TradeOrder order = new TradeOrder();
		order.setId(id);
		//持久化stateMachine
		toStateMachinePersister.persist(stateMachine, order.getId());

	}
	/**
	 * 从内存恢复状态机,不适合分布式.
	 * @param id
	 * @throws Exception
	 */
	@RequestMapping("/restorePersisterRestore")
	public void testMemoryRestore(String id) throws Exception {
		StateMachine<TradeOrderStates, TradeOrderEvents> stateMachine = toStateMachineBuilder.build();
		toStateMachinePersister.restore(stateMachine, id);
		logger.info("恢复状态机后的状态为：" + stateMachine.getState().getId());
	}
	/**
	 * Fay 存储状态机
	 * 保存状态机至Redis
	 * @param id
	 * @throws Exception
	 */
	@RequestMapping("/saveRedis")
	public void saveRedis(String id,String state) throws Exception {
		StateMachine<TradeOrderStates, TradeOrderEvents> stateMachine = toStateMachineBuilder.build();
		stateMachine.start();
		TradeOrder order = new TradeOrder();
		order.setId(id);
		order.setState(state);
		toStateMachinePersister.restore(stateMachine,order);
		//发送PAY事件
		Message<TradeOrderEvents> message = MessageBuilder.withPayload(TradeOrderEvents.CUSTOMER_SUBMIT).setHeader("order", order).build();
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
		StateMachine<TradeOrderStates, TradeOrderEvents> stateMachine = toStateMachineBuilder.build();
		stateMachineRedisPersister.restore(stateMachine, id);
		logger.info("状态机ID：" + "stateMachineID: "+ id+" 恢复时状态为"+stateMachine.getState().getId());
	}
}
