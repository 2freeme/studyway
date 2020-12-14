package com.trade.statemachine.tox;
import com.trade.statemachine.pso_refund.PostSaleOrderRefundChoice;
import com.trade.statemachine.to.TradeOrderChoice;
import com.trade.statemachine.to.TradeOrderEvents;
import com.trade.statemachine.to.TradeOrderStates;
import com.trade.statemachine.web.entity.TradeOrderX;
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
@EnableStateMachine(name= TradeOrderXBuilder.MACHINEID_TOX)
public class TradeOrderXBuilder {
	@Autowired
	private BeanFactory beanFactory;
	private Logger logger = LoggerFactory.getLogger(getClass());
	public final static String MACHINEID_TOX = "MACHINEID_TOX";//TO状态机
	public StateMachine<TradeOrderXStates, TradeOrderXEvents> build() throws Exception {
		StateMachine<TradeOrderXStates, TradeOrderXEvents> stateMachine = build(beanFactory);
		logger.info("状态机ID："+stateMachine.getId());
		stateMachine.start();
		return stateMachine;
	}
	public StateMachine<TradeOrderXStates, TradeOrderXEvents> build(BeanFactory beanFactory) throws Exception {
		StateMachineBuilder.Builder<TradeOrderXStates, TradeOrderXEvents> builder = StateMachineBuilder.builder();
		builder.configureConfiguration()
				.withConfiguration()
				.machineId(MACHINEID_TOX)
				.beanFactory(beanFactory);
		builder.configureStates()
				.withStates()
				.initial(TradeOrderXStates.CREATE)
				.choice(TradeOrderXStates.CHOICE_ALL_CANCELLED)
				.states(EnumSet.allOf(TradeOrderXStates.class));
		//1增加Choice, 2增加初始状态Choice, 3.增加OrderEvenConfig  4.Controll测试虚拟出状态机,并restore正确状态.
		builder.configureTransitions()
				.withExternal().source(TradeOrderXStates.CREATE).target(TradeOrderXStates.WAIT_FOR_PAY).event(TradeOrderXEvents.CREATE_TOX).and()
				.withExternal().source(TradeOrderXStates.WAIT_FOR_PAY).target(TradeOrderXStates.WAIT_FOR_AUDIT).event(TradeOrderXEvents.CUSTOMER_COMPLEMENT_LINE).and()
				//状态图缺失了此条,状态机增加,图未同步更新.
				.withExternal().source(TradeOrderXStates.WAIT_FOR_PAY).target(TradeOrderXStates.CLOSED).event(TradeOrderXEvents.CUSTOMER_INITIATED_RETURN_AND_BUSINESS_LINE_NOT_ENOUGH).and()
				.withExternal().source(TradeOrderXStates.WAIT_FOR_AUDIT).target(TradeOrderXStates.CHOICE_AUDIT_FINISH).event(TradeOrderXEvents.AUDIT_BY_AUTO).and()
				.withExternal().source(TradeOrderXStates.WAIT_FOR_AUDIT).target(TradeOrderXStates.CHOICE_AUDIT_FINISH).event(TradeOrderXEvents.AUDIT_BY_BUSINESS).and()
				.withExternal().source(TradeOrderXStates.WAIT_FOR_AUDIT).target(TradeOrderXStates.CHOICE_ALL_CANCELLED).event(TradeOrderXEvents.BUSINESS_CANCELLED).and()
				.withChoice().source(TradeOrderXStates.CHOICE_ALL_CANCELLED).first(TradeOrderXStates.CLOSED, new TradeOrderXChoice()).last(TradeOrderXStates.WAIT_FOR_AUDIT).and()
				.withChoice().source(TradeOrderXStates.CHOICE_AUDIT_FINISH).first(TradeOrderXStates.WAIT_FOR_DELIVER, new TradeOrderXChoice()).last(TradeOrderXStates.AUDIT_DELIVER_PARTICAL).and()
				//是否发货完毕或无需发货,存在三个分支,其两个否分支重新拆分成新的CHOICE_NUM_OF_SEND_GT0(待发数是否大于0)状态
				//是否发货完毕或无需发货, 所有此状态都先进入"是否评审完毕",再进行CHOICE_NUM_OF_SEND_GT0判定.(此处状态图有两处不符)
				.withChoice().source(TradeOrderXStates.CHOICE_ALL_BUSINESS_SENT_OR_NOTO).first(TradeOrderXStates.COMPLETED, new TradeOrderXChoice()).last(TradeOrderXStates.CHOICE_AUDIT_FINISH).and()
				.withChoice().source(TradeOrderXStates.CHOICE_NUM_OF_SEND_GT0).first(TradeOrderXStates.COMPLETED, new TradeOrderXChoice()).last(TradeOrderXStates.WAIT_FOR_DELIVER).and()
				.withChoice().source(TradeOrderXStates.CHOICE_NUM_OF_SEND_GT0).first(TradeOrderXStates.COMPLETED, new TradeOrderXChoice()).last(TradeOrderXStates.CLOSED).and()
				.withExternal().source(TradeOrderXStates.AUDIT_DELIVER_PARTICAL).target(TradeOrderXStates.AUDIT_DELIVER_PARTICAL).event(TradeOrderXEvents.BUSINESS_SEND_CONFIRM).and()
				.withExternal().source(TradeOrderXStates.AUDIT_DELIVER_PARTICAL).target(TradeOrderXStates.CHOICE_AUDIT_FINISH).event(TradeOrderXEvents.AUDIT_BY_BUSINESS).and()
				.withExternal().source(TradeOrderXStates.AUDIT_DELIVER_PARTICAL).target(TradeOrderXStates.CHOICE_ALL_BUSINESS_SENT_OR_NOTO).event(TradeOrderXEvents.BUSINESS_CANCELLED).and()
				.withExternal().source(TradeOrderXStates.WAIT_FOR_DELIVER).target(TradeOrderXStates.CHOICE_ALL_BUSINESS_SENT_OR_NOTO).event(TradeOrderXEvents.BUSINESS_INTERCEPT_CONFIRM).and()
				.withExternal().source(TradeOrderXStates.WAIT_FOR_DELIVER).target(TradeOrderXStates.CHOICE_ALL_BUSINESS_SENT_OR_NOTO).event(TradeOrderXEvents.BUSINESS_SEND_CONFIRM);
		return builder.build();
	}
	@Bean(name = "tradeOrderXPersister")
	public StateMachinePersister<TradeOrderXStates, TradeOrderXEvents, TradeOrderX> getOrderPersister() {
		return new DefaultStateMachinePersister<>(new StateMachinePersist<TradeOrderXStates, TradeOrderXEvents, TradeOrderX>() {
			@Override
			public void write(StateMachineContext<TradeOrderXStates, TradeOrderXEvents> context, TradeOrderX contextObj) {
			}
			@Override
			public StateMachineContext<TradeOrderXStates, TradeOrderXEvents> read(TradeOrderX contextObj) {
				StateMachineContext<TradeOrderXStates, TradeOrderXEvents> result = new DefaultStateMachineContext(TradeOrderXStates.valueOf(contextObj.getState()),
						null, null, null, null, MACHINEID_TOX);
				return result;
			}
			;
		});
	}
}