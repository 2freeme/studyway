package com.trade.statemachine.pso_refund;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
/**
 * 状态机,业务层调用
 */
@WithStateMachine(id= PostSaleOrderRefundBuilder.MACHINEID_PSO_REFUND)
public class PostSaleOrderRefundAction {
private Logger logger = LoggerFactory.getLogger(getClass());
    @OnTransition(source = "CREATE", target = "WAIT_FOR_AUDIT")
    public void CUSTOMER_INITIATED_RETURN(Message<PostSaleOrderRefundEvents> message) {
        logger.info("用户发起退货申请");
        logger.info("发生了["+message.getPayload()+"],状态从[CREATE]  变成  [WAIT_FOR_AUDIT]");
    }

    @OnTransition(source = "WAIT_FOR_AUDIT", target = "CLOSED")
    public void CUSTOMER_CANCELLED(Message<PostSaleOrderRefundEvents> message) {
        logger.info("用户取消");
        logger.info("发生了["+message.getPayload()+"],状态从[WAIT_FOR_AUDIT]  变成  [CLOSED]");
    }
    @OnTransition(source = "WAIT_FOR_AUDIT", target = "CLOSED")
    public void BUSINESS_CANCELLED(Message<PostSaleOrderRefundEvents> message) {
        logger.info("商家驳回或取消");
        logger.info("发生了["+message.getPayload()+"],状态从[WAIT_FOR_AUDIT]  变成  [CLOSED]");
    }
    @OnTransition(source = "WAIT_FOR_AUDIT", target = "AUDIT_PARTICALLY")
    public void BUSINESS_AUDIT(Message<PostSaleOrderRefundEvents> message) {
        logger.info("商家评审");
        logger.info("发生了["+message.getPayload()+"],状态从[WAIT_FOR_AUDIT]  变成  [AUDIT_PARTICALLY]");
    }
    @OnTransition(source = "WAIT_FOR_RETURN", target = "COMPLETED")
    public void CONFIRM_REFUND_INBOUND(Message<PostSaleOrderRefundEvents> message) {
        logger.info("确认退货入库");
        logger.info("发生了["+message.getPayload()+"],状态从[WAIT_FOR_RETURN]  变成  [COMPLETED]");
    }
}
