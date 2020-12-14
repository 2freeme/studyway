package com.trade.statemachine.tox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
/**
 * 状态机,业务层调用
 */
@WithStateMachine(id= TradeOrderXBuilder.MACHINEID_TOX)
public class TradeOrderXAction {
private Logger logger = LoggerFactory.getLogger(getClass());
    @OnTransition(source = "CREATE", target = "WAIT_FOR_AUDIT")
    public void CUSTOMER_INITIATED_RETURN(Message<TradeOrderXEvents> message) {
        logger.info("用户发起退货申请");
        logger.info("发生了["+message.getPayload()+"],状态从[CREATE]  变成  [WAIT_FOR_AUDIT]");
    }
}
