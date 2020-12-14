package com.trade.statemachine.to;
import com.trade.statemachine.web.entity.TradeOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.action.Action;
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
@EnableStateMachine(name= TradeOrderMachineBuilder.MACHINEID_TO)
public class TradeOrderMachineBuilder {
	@Autowired
	private BeanFactory beanFactory;
	private Logger logger = LoggerFactory.getLogger(getClass());
	public final static String MACHINEID_TO = "MACHINEID_TO";//TO状态机
	public StateMachine<TradeOrderStates, TradeOrderEvents> build() throws Exception {
		StateMachine<TradeOrderStates, TradeOrderEvents> stateMachine = build(beanFactory);
		logger.info("状态机ID："+stateMachine.getId());
		stateMachine.start();
		return stateMachine;
	}
	 /**
	  * 构建状态机
	  * -构建TO单状态机
	  * E:\业务资料\状态机\正向门店TO.jpg
	 * @param beanFactory
	 * @return
	 * @throws Exception
	 */
	public StateMachine<TradeOrderStates, TradeOrderEvents> build(BeanFactory beanFactory) throws Exception {
		 StateMachineBuilder.Builder<TradeOrderStates, TradeOrderEvents> builder = StateMachineBuilder.builder();
		 builder.configureConfiguration()
		 		.withConfiguration()
		 		.machineId(MACHINEID_TO)
		 		.beanFactory(beanFactory);
		 
		 builder.configureStates()
		 			.withStates()
		 			.initial(TradeOrderStates.CREATE)
				 	.choice(TradeOrderStates.CHOICE_GROUP_RAISED)
				 	.choice(TradeOrderStates.CHOICE_DISPATCH_ALL)
				 	.choice(TradeOrderStates.CHOICE_ALL_CANCELLED)
				 	.choice(TradeOrderStates.CHOICE_ALL_BUSINESS_SENT_OR_NOTO)
				 	.choice(TradeOrderStates.CHOICE_AUDIT_FINISH)
				 	.choice(TradeOrderStates.CHOICE_EXTRA_MONEY)
				 	.choice(TradeOrderStates.CHOICE_STORE_SENT_OR_INDEED)
		 			.states(EnumSet.allOf(TradeOrderStates.class));
		 			//1增加Choice, 2增加初始状态Choice, 3.增加OrderEvenConfig  4.Controll测试虚拟出状态机,并restore正确状态.
		 builder.configureTransitions()
			.withExternal().source(TradeOrderStates.CREATE).target(TradeOrderStates.WAIT_FOR_PAY).event(TradeOrderEvents.CUSTOMER_SUBMIT).and()
			.withExternal().source(TradeOrderStates.WAIT_FOR_PAY).target(TradeOrderStates.CLOSED).event(TradeOrderEvents.DISPATCHER_OVERTIME).and()
			.withExternal().source(TradeOrderStates.WAIT_FOR_PAY).target(TradeOrderStates.CLOSED).event(TradeOrderEvents.CUSTOMER_CANCEL).and()
			.withExternal().source(TradeOrderStates.WAIT_FOR_PAY).target(TradeOrderStates.CHOICE_GROUP_RAISED).event(TradeOrderEvents.CUSTOMER_PAY).and()
			.withChoice().source(TradeOrderStates.CHOICE_GROUP_RAISED).first(TradeOrderStates.WAIT_FOR_GROUP, new TradeOrderChoice()).last(TradeOrderStates.CHOICE_DISPATCH_ALL).and()
			.withChoice().source(TradeOrderStates.CHOICE_DISPATCH_ALL).first(TradeOrderStates.WAIT_FOR_DELIVER_OTHERS, new TradeOrderChoice()).last(TradeOrderStates.WAIT_FOR_AUDIT).and()
			.withExternal().source(TradeOrderStates.WAIT_FOR_AUDIT).target(TradeOrderStates.CHOICE_ALL_CANCELLED).event(TradeOrderEvents.BUSINESS_CANCELLED).and()
			.withChoice().source(TradeOrderStates.CHOICE_ALL_CANCELLED).first(TradeOrderStates.CLOSED, new TradeOrderChoice()).last(TradeOrderStates.WAIT_FOR_AUDIT).and()
			.withExternal().source(TradeOrderStates.WAIT_FOR_GROUP).target(TradeOrderStates.CLOSED).event(TradeOrderEvents.GROUP_FAILD).and()
			.withExternal().source(TradeOrderStates.WAIT_FOR_GROUP).target(TradeOrderStates.CHOICE_EXTRA_MONEY).event(TradeOrderEvents.GROUP_SUCCES).and()
			.withChoice().source(TradeOrderStates.CHOICE_EXTRA_MONEY).first(TradeOrderStates.WAIT_FOR_PAY_REST, new TradeOrderChoice()).last(TradeOrderStates.CHOICE_DISPATCH_ALL).and()
			.withExternal().source(TradeOrderStates.WAIT_FOR_PAY_REST).target(TradeOrderStates.CHOICE_DISPATCH_ALL).event(TradeOrderEvents.CUSTOMER_PAY).and()
			.withExternal().source(TradeOrderStates.WAIT_FOR_PAY_REST).target(TradeOrderStates.CLOSED).event(TradeOrderEvents.CUSTOMER_PAY_CANCEL).event(TradeOrderEvents.DISPATCHER_OVERTIME).and()
			.withExternal().source(TradeOrderStates.WAIT_FOR_AUDIT).target(TradeOrderStates.CHOICE_AUDIT_FINISH).event(TradeOrderEvents.AUDIT_BY_BUSINESS).event(TradeOrderEvents.AUDIT_BY_AUTO).and()
			.withChoice().source(TradeOrderStates.CHOICE_AUDIT_FINISH).first(TradeOrderStates.WAIT_FOR_DELIVER, new TradeOrderChoice()).last(TradeOrderStates.AUDIT_DELIVER_PARTICAL).and()
			.withExternal().source(TradeOrderStates.AUDIT_DELIVER_PARTICAL).target(TradeOrderStates.AUDIT_DELIVER_PARTICAL).event(TradeOrderEvents.BUSINESS_SEND_CONFIRM).and()
			.withExternal().source(TradeOrderStates.AUDIT_DELIVER_PARTICAL).target(TradeOrderStates.CHOICE_AUDIT_FINISH).event(TradeOrderEvents.AUDIT_BY_BUSINESS).and()
			.withExternal().source(TradeOrderStates.AUDIT_DELIVER_PARTICAL).target(TradeOrderStates.CHOICE_ALL_BUSINESS_SENT_OR_NOTO).event(TradeOrderEvents.BUSINESS_CANCELLED).and()
			.withExternal().source(TradeOrderStates.WAIT_FOR_DELIVER).target(TradeOrderStates.CHOICE_ALL_BUSINESS_SENT_OR_NOTO).event(TradeOrderEvents.BUSINESS_INTERCEPT_CONFIRM).and()
			.withChoice().source(TradeOrderStates.CHOICE_ALL_BUSINESS_SENT_OR_NOTO).first(TradeOrderStates.WAIT_FOR_SIGN, new TradeOrderChoice()).last(TradeOrderStates.CHOICE_STORE_SENT_OR_INDEED).and()
			.withChoice().source(TradeOrderStates.CHOICE_STORE_SENT_OR_INDEED).first(TradeOrderStates.WAIT_FOR_DELIVER_OTHERS, new TradeOrderChoice()).last(TradeOrderStates.WAIT_FOR_DELIVER).and()
			.withExternal().source(TradeOrderStates.WAIT_FOR_DELIVER_OTHERS).target(TradeOrderStates.CHOICE_ALL_BUSINESS_SENT_OR_NOTO).event(TradeOrderEvents.OTHER_BUSINESS_SENT).and()
			.withExternal().source(TradeOrderStates.WAIT_FOR_SIGN).target(TradeOrderStates.WAIT_FOR_EVALUATE).event(TradeOrderEvents.SIGN_CUSTOMER).event(TradeOrderEvents.SIGN_AUTO).and()
			.withExternal().source(TradeOrderStates.WAIT_FOR_EVALUATE).target(TradeOrderStates.COMPLETED).event(TradeOrderEvents.EVALUATE_CUSTOMER).event(TradeOrderEvents.EVALUATE_AUTO).and();
		return builder.build();
	 }

	@Bean
    public Action<TradeOrderStates, TradeOrderEvents> toAction() {
        return new Action<TradeOrderStates, TradeOrderEvents>() {
            @Override
            public void execute(StateContext<TradeOrderStates, TradeOrderEvents> context) {
            	logger.info("状态机Builder中toAction："+context.getEvent());
            }
        };
    }

	@Bean(name = "toStateMachinePersister")
	public StateMachinePersister<TradeOrderStates, TradeOrderEvents, TradeOrder> getOrderPersister() {
		return new DefaultStateMachinePersister<>(new StateMachinePersist<TradeOrderStates, TradeOrderEvents, TradeOrder>() {
			@Override
			public void write(StateMachineContext<TradeOrderStates, TradeOrderEvents> context, TradeOrder contextObj) {
			}
			@Override
			public StateMachineContext<TradeOrderStates, TradeOrderEvents> read(TradeOrder contextObj) {
				StateMachineContext<TradeOrderStates, TradeOrderEvents> result = new DefaultStateMachineContext(TradeOrderStates.valueOf(contextObj.getState()),
						null, null, null, null, MACHINEID_TO);
				return result;
			}
			;
		});
	}
}