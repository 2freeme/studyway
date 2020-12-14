package com.trade.statemachine.pso_refund;
import com.trade.statemachine.web.entity.PostSaleOrderRefund;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;

import java.util.EnumSet;
/**
 * Fay 2020年12月5日 正向门店TO
 */
@Component
@EnableStateMachine(name= PostSaleOrderRefundBuilder.MACHINEID_PSO_REFUND)
public class PostSaleOrderRefundBuilder {
	@Autowired
	private BeanFactory beanFactory;
	private Logger logger = LoggerFactory.getLogger(getClass());
	public final static String MACHINEID_PSO_REFUND = "MACHINEID_PSO_REFUND";//TO状态机
	public StateMachine<PostSaleOrderRefundStates, PostSaleOrderRefundEvents> build() throws Exception {
		StateMachine<PostSaleOrderRefundStates, PostSaleOrderRefundEvents> stateMachine = build(beanFactory);
		logger.info("状态机ID："+stateMachine.getId());
		stateMachine.start();
		return stateMachine;
	}
	public StateMachine<PostSaleOrderRefundStates, PostSaleOrderRefundEvents> build(BeanFactory beanFactory) throws Exception {
		StateMachineBuilder.Builder<PostSaleOrderRefundStates, PostSaleOrderRefundEvents> builder = StateMachineBuilder.builder();
		builder.configureConfiguration()
				.withConfiguration()
				.machineId(MACHINEID_PSO_REFUND)
				.beanFactory(beanFactory);
		builder.configureStates()
				.withStates()
				.initial(PostSaleOrderRefundStates.CREATE)
				.states(EnumSet.allOf(PostSaleOrderRefundStates.class));
		//1增加Choice, 2增加初始状态Choice, 3.增加OrderEvenConfig  4.Controll测试虚拟出状态机,并restore正确状态.
		builder.configureTransitions()
				.withExternal().source(PostSaleOrderRefundStates.CREATE).target(PostSaleOrderRefundStates.WAIT_FOR_AUDIT).event(PostSaleOrderRefundEvents.CUSTOMER_INITIATED_RETURN).and()
				.withExternal().source(PostSaleOrderRefundStates.WAIT_FOR_AUDIT).target(PostSaleOrderRefundStates.CLOSED).event(PostSaleOrderRefundEvents.CUSTOMER_CANCELLED).and()
				.withExternal().source(PostSaleOrderRefundStates.WAIT_FOR_AUDIT).target(PostSaleOrderRefundStates.CLOSED).event(PostSaleOrderRefundEvents.BUSINESS_CANCELLED).and()
//				.withExternal().source(PostSaleOrderRefundStates.WAIT_FOR_AUDIT).target(PostSaleOrderRefundStates.COMPLETED).and()
				.withExternal().source(PostSaleOrderRefundStates.WAIT_FOR_AUDIT).target(PostSaleOrderRefundStates.AUDIT_PARTICALLY).event(PostSaleOrderRefundEvents.BUSINESS_AUDIT).and()
				.withExternal().source(PostSaleOrderRefundStates.AUDIT_PARTICALLY).target(PostSaleOrderRefundStates.WAIT_FOR_RETURN).and()
				.withExternal().source(PostSaleOrderRefundStates.AUDIT_PARTICALLY).target(PostSaleOrderRefundStates.COMPLETED).and()
				.withExternal().source(PostSaleOrderRefundStates.WAIT_FOR_RETURN).target(PostSaleOrderRefundStates.WAIT_FOR_REFOUND).and()
				.withExternal().source(PostSaleOrderRefundStates.WAIT_FOR_RETURN).target(PostSaleOrderRefundStates.COMPLETED).event(PostSaleOrderRefundEvents.CONFIRM_REFUND_INBOUND).and()
				.withExternal().source(PostSaleOrderRefundStates.WAIT_FOR_REFOUND).target(PostSaleOrderRefundStates.COMPLETED);
		return builder.build();
	}
	@Bean(name = "postSaleOrderRefundPersister")
	public StateMachinePersister<PostSaleOrderRefundStates, PostSaleOrderRefundEvents, PostSaleOrderRefund> getOrderPersister() {
		return new DefaultStateMachinePersister<>(new StateMachinePersist<PostSaleOrderRefundStates, PostSaleOrderRefundEvents, PostSaleOrderRefund>() {
			@Override
			public void write(StateMachineContext<PostSaleOrderRefundStates, PostSaleOrderRefundEvents> context, PostSaleOrderRefund contextObj) {
			}
			@Override
			public StateMachineContext<PostSaleOrderRefundStates, PostSaleOrderRefundEvents> read(PostSaleOrderRefund contextObj) {
				StateMachineContext<PostSaleOrderRefundStates, PostSaleOrderRefundEvents> result = new DefaultStateMachineContext(PostSaleOrderRefundStates.valueOf(contextObj.getState()),
						null, null, null, null, MACHINEID_PSO_REFUND);
				return result;
			}
			;
		});
	}
}