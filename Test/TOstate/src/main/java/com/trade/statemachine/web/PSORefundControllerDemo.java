package com.trade.statemachine.web;
import com.trade.statemachine.pso_refund.PostSaleOrderRefundBuilder;
import com.trade.statemachine.pso_refund.PostSaleOrderRefundEvents;
import com.trade.statemachine.pso_refund.PostSaleOrderRefundStates;
import com.trade.statemachine.to.TradeOrderStates;
import com.trade.statemachine.web.entity.PostSaleOrderRefund;
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
/**
 * 派生单TOX状态机调用演示类
 * 使用方法：
 * 1.实体实现IPersist接口
 * 2.读取当前实体状态,初始化状态机
 * 3.用状态机发送事件,并在Action(对应状态图中的事件)和Choice(对应状态图的分支)调用实现类.(无须关心是否成功)
 */


@RestController
@RequestMapping("/statemachine/pso")
public class PSORefundControllerDemo {
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private PostSaleOrderRefundBuilder postSaleOrderRefundBuilder;
	@Resource(name = "stateMachineRedisPersister")
	private StateMachinePersister stateMachineRedisPersister;
	@Resource(name = "postSaleOrderRefundPersister")
	private StateMachinePersister postSaleOrderRefundPersister;

	@RequestMapping("/createOrder")
	public void createOrder(String orderId) throws Exception {
		StateMachine<PostSaleOrderRefundStates, PostSaleOrderRefundEvents> stateMachine = postSaleOrderRefundBuilder.build();
		PostSaleOrderRefund order = new PostSaleOrderRefund(orderId);
		order.setState(PostSaleOrderRefundStates.CREATE.name());
		postSaleOrderRefundPersister.restore(stateMachine,order);
		stateMachine.sendEvent(PostSaleOrderRefundEvents.CUSTOMER_INITIATED_RETURN);
		stateMachineRedisPersister.persist(stateMachine,order.getId());
		logger.info("最终状态：" + stateMachine.getState().getId());
	}

	/**
	 * 订单取消
	 * @param orderId
	 * @throws Exception
	 */
	@RequestMapping("/audit")
	public void audit(String orderId) throws Exception {
		StateMachine<PostSaleOrderRefundStates, PostSaleOrderRefundEvents> stateMachine = postSaleOrderRefundBuilder.build();
		PostSaleOrderRefund order=new PostSaleOrderRefund("c1",true,PostSaleOrderRefundStates.WAIT_FOR_AUDIT.name());
		postSaleOrderRefundPersister.restore(stateMachine,order);

		Message<PostSaleOrderRefundEvents> message = MessageBuilder.withPayload(PostSaleOrderRefundEvents.BUSINESS_AUDIT).setHeader("orderId", order).build();
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
		StateMachine<PostSaleOrderRefundStates, PostSaleOrderRefundEvents> stateMachine = postSaleOrderRefundBuilder.build();
		PostSaleOrderRefund order = new PostSaleOrderRefund(orderId, true, TradeOrderStates.WAIT_FOR_PAY.name());
		postSaleOrderRefundPersister.restore(stateMachine,order);
		Message<PostSaleOrderRefundEvents> message = MessageBuilder.withPayload(PostSaleOrderRefundEvents.BUSINESS_AUDIT).setHeader("orderId", order).build();
		stateMachine.sendEvent(message);
		logger.info("最终状态：" + stateMachine.getState().getId());
	}
}
