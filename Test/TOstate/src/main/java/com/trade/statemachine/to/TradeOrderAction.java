package com.trade.statemachine.to;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
/**
 * 状态机,业务层调用
 */
@WithStateMachine(id= TradeOrderMachineBuilder.MACHINEID_TO)
public class TradeOrderAction{
private Logger logger = LoggerFactory.getLogger(getClass());
    @OnTransition(target = "UNPAID")
    public void create() {
        logger.info("---订单创建，待支付---");
    }

    /////////////////////////////////////////////////////////////
    @OnTransition(source = "CREATE", target = "WAIT_FOR_PAY")
    public void CUSTOMER_SUBMIT(Message<TradeOrderEvents> message) {
        logger.info("用户提单");
        logger.info("发生了["+message.getPayload()+"],状态从[CREATE]  变成  [WAIT_FOR_PAY]");
    }
    @OnTransition(source = "WAIT_FOR_PAY", target = "CLOSED")
    public void CUSTOMER_CANCEL(Message<TradeOrderEvents> message) {
        logger.info("用户取消了订单,执行了释放锁定库存");
        logger.info("发生了["+message.getPayload()+"],状态从[WAIT_FOR_PAY]  变成  [CLOSED]");
    }
    @OnTransition(source = "WAIT_FOR_PAY", target = "CHOICE_GROUP_RAISED")
    public void CUSTOMER_PAY(Message<TradeOrderEvents> message) {
        logger.info("这里实现客户付款逻辑,发起支付,更新支付状态,生成派生TO");
        logger.info("传递的参数：" + message.getHeaders().get("orderId"));
        logger.info("发生了["+message.getPayload()+"],状态从[WAIT_FOR_PAY]  变成  [CHOICE_GROUP_RAISED]");
    }
    @OnTransition(source = "CHOICE_GROUP_RAISED", target = "WAIT_FOR_GROUP")
    public void CHOICE_GROUP_RAISED0(Message<TradeOrderEvents> message) {
        logger.info("传递的参数：" + message.getHeaders().get("OrderTO"));
        logger.info("传递的参数：" + message.getHeaders().get("userID"));
        logger.info("发生了["+message.getPayload()+"],状态从[CHOICE_GROUP_RAISED]  变成  [WAIT_FOR_GROUP]");
    }
    @OnTransition(source = "CHOICE_GROUP_RAISED", target = "CHOICE_DISPATCH_ALL")
    public void CHOICE_GROUP_RAISED1(Message<TradeOrderEvents> message) {
        logger.info("传递的参数：" + message.getHeaders().get("OrderTO"));
        logger.info("传递的参数：" + message.getHeaders().get("userID"));
        logger.info("发生了["+message.getPayload()+"],状态从[CHOICE_GROUP_RAISED]  变成  [CHOICE_DISPATCH_ALL]");
    }
    @OnTransition(source = "CHOICE_DISPATCH_ALL", target = "WAIT_FOR_DELIVER_OTHERS")
    public void CHOICE_DISPATCH_ALL0(Message<TradeOrderEvents> message) {
        logger.info("传递的参数：" + message.getHeaders().get("OrderTO"));
        logger.info("传递的参数：" + message.getHeaders().get("userID"));
        logger.info("发生了["+message.getPayload()+"],状态从[CHOICE_DISPATCH_ALL]  变成  [WAIT_FOR_DELIVER_OTHERS]");
    }
    @OnTransition(source = "CHOICE_DISPATCH_ALL", target = "WAIT_FOR_AUDIT")
    public void CHOICE_DISPATCH_ALL1(Message<TradeOrderEvents> message) {
        logger.info("传递的参数：" + message.getHeaders().get("OrderTO"));
        logger.info("传递的参数：" + message.getHeaders().get("userID"));
        logger.info("发生了["+message.getPayload()+"],状态从[CHOICE_DISPATCH_ALL]  变成  [WAIT_FOR_AUDIT]");
    }
    @OnTransition(source = "CHOICE_ALL_CANCELLED", target = "CLOSED")
    public void CHOICE_ALL_CANCELLED0(Message<TradeOrderEvents> message) {
        logger.info("传递的参数：" + message.getHeaders().get("OrderTO"));
        logger.info("传递的参数：" + message.getHeaders().get("userID"));
        logger.info("发生了["+message.getPayload()+"],状态从[CHOICE_ALL_CANCELLED]  变成  [CLOSED]");
    }
    @OnTransition(source = "CHOICE_ALL_CANCELLED", target = "WAIT_FOR_AUDIT")
    public void CHOICE_ALL_CANCELLED1(Message<TradeOrderEvents> message) {
        logger.info("传递的参数：" + message.getHeaders().get("OrderTO"));
        logger.info("传递的参数：" + message.getHeaders().get("userID"));
        logger.info("发生了["+message.getPayload()+"],状态从[CHOICE_ALL_CANCELLED]  变成  [WAIT_FOR_AUDIT]");
    }
    @OnTransition(source = "WAIT_FOR_GROUP", target = "CLOSED")
    public void GROUP_FAILD(Message<TradeOrderEvents> message) {
        logger.info("发生了["+message.getPayload()+"],状态从[WAIT_FOR_GROUP]  变成  [CLOSED]");
    }
    @OnTransition(source = "WAIT_FOR_GROUP", target = "CHOICE_EXTRA_MONEY")
    public void GROUP_SUCCES(Message<TradeOrderEvents> message) {
        logger.info("发生了["+message.getPayload()+"],状态从[WAIT_FOR_GROUP]  变成  [CHOICE_EXTRA_MONEY]");
    }
    @OnTransition(source = "CHOICE_EXTRA_MONEY", target = "WAIT_FOR_PAY_REST")
    public void CHOICE_EXTRA_MONEY0(Message<TradeOrderEvents> message) {
        logger.info("发生了["+message.getPayload()+"],状态从[CHOICE_EXTRA_MONEY]  变成  [WAIT_FOR_PAY_REST]");
    }
    @OnTransition(source = "CHOICE_EXTRA_MONEY", target = "CHOICE_DISPATCH_ALL")
    public void CHOICE_EXTRA_MONEY1(Message<TradeOrderEvents> message) {
        logger.info("发生了["+message.getPayload()+"],状态从[]  变成  [CHOICE_DISPATCH_ALL]");
    }
    @OnTransition(source = "WAIT_FOR_PAY_REST", target = "CHOICE_DISPATCH_ALL")
    public void CUSTOMER_PAY2(Message<TradeOrderEvents> message) {
        logger.info("发生了["+message.getPayload()+"],状态从[WAIT_FOR_PAY_REST]  变成  [CHOICE_DISPATCH_ALL]");
    }
    @OnTransition(source = "WAIT_FOR_PAY_REST", target = "CLOSED")
    public void CUSTOMER_PAY_CANCEL(Message<TradeOrderEvents> message) {
        logger.info("发生了["+message.getPayload()+"],状态从[WAIT_FOR_PAY_REST]  变成  [CLOSED]");
    }
    @OnTransition(source = "WAIT_FOR_PAY_REST", target = "CLOSED")
    public void DISPATCHER_OVERTIME(Message<TradeOrderEvents> message) {
        logger.info("发生了["+message.getPayload()+"],状态从[WAIT_FOR_PAY_REST]  变成  [CLOSED]");
    }
    @OnTransition(source = "WAIT_FOR_AUDIT", target = "CHOICE_AUDIT_FINISH")
    public void AUDIT_BY_AUTO(Message<TradeOrderEvents> message) {
        logger.info("发生了["+message.getPayload()+"],状态从[WAIT_FOR_AUDIT]  变成  [CHOICE_AUDIT_FINISH]");
    }
    @OnTransition(source = "AUDIT_DELIVER_PARTICAL", target = "AUDIT_DELIVER_PARTICAL")
    public void BUSINESS_SEND_CONFIRM(Message<TradeOrderEvents> message) {
        logger.info("发生了["+message.getPayload()+"],状态从[AUDIT_DELIVER_PARTICAL]  变成  [AUDIT_DELIVER_PARTICAL]");
    }
    @OnTransition(source = "AUDIT_DELIVER_PARTICAL", target = "CHOICE_AUDIT_FINISH")
    public void AUDIT_BY_BUSINESS(Message<TradeOrderEvents> message) {
        logger.info("发生了["+message.getPayload()+"],状态从[AUDIT_DELIVER_PARTICAL]  变成  [CHOICE_AUDIT_FINISH]");
    }
    @OnTransition(source = "AUDIT_DELIVER_PARTICAL", target = "CHOICE_ALL_BUSINESS_SENT_OR_NOTO")
    public void BUSINESS_CANCELLED(Message<TradeOrderEvents> message) {
        logger.info("发生了["+message.getPayload()+"],状态从[AUDIT_DELIVER_PARTICAL]  变成  [CHOICE_ALL_BUSINESS_SENT_OR_NOTO]");
    }
    @OnTransition(source = "WAIT_FOR_DELIVER", target = "CHOICE_ALL_BUSINESS_SENT_OR_NOTO")
    public void BUSINESS_INTERCEPT_CONFIRM(Message<TradeOrderEvents> message) {
        logger.info("发生了["+message.getPayload()+"],状态从[WAIT_FOR_DELIVER]  变成  [CHOICE_ALL_BUSINESS_SENT_OR_NOTO]");
    }
    @OnTransition(source = "WAIT_FOR_DELIVER_OTHERS", target = "CHOICE_ALL_BUSINESS_SENT_OR_NOTO")
    public void OTHER_BUSINESS_SENT(Message<TradeOrderEvents> message) {
        logger.info("发生了["+message.getPayload()+"],状态从[WAIT_FOR_DELIVER_OTHERS]  变成  [CHOICE_ALL_BUSINESS_SENT_OR_NOTO]");
    }
    @OnTransition(source = "WAIT_FOR_SIGN", target = "WAIT_FOR_EVALUATE")
    public void SIGN_CUSTOMER(Message<TradeOrderEvents> message) {
        logger.info("发生了["+message.getPayload()+"],状态从[WAIT_FOR_SIGN]  变成  [WAIT_FOR_EVALUATE]");
    }
    @OnTransition(source = "WAIT_FOR_SIGN", target = "WAIT_FOR_EVALUATE")
    public void SIGN_AUTO(Message<TradeOrderEvents> message) {
        logger.info("发生了["+message.getPayload()+"],状态从[WAIT_FOR_SIGN]  变成  [WAIT_FOR_EVALUATE]");
    }
    @OnTransition(source = "WAIT_FOR_EVALUATE", target = "COMPLETED")
    public void EVALUATE_CUSTOMER(Message<TradeOrderEvents> message) {
        logger.info("发生了["+message.getPayload()+"],状态从[WAIT_FOR_EVALUATE]  变成  [COMPLETED]");
    }
    @OnTransition(source = "WAIT_FOR_EVALUATE", target = "COMPLETED")
    public void EVALUATE_AUTO(Message<TradeOrderEvents> message) {
        logger.info("发生了["+message.getPayload()+"],状态从[WAIT_FOR_EVALUATE]  变成  [COMPLETED]");
    }
    @OnTransition(source = "CHOICE_AUDIT_FINISH", target = "WAIT_FOR_DELIVER")
    public void CHOICE_AUDIT_FINISH0(Message<TradeOrderEvents> message) {
        logger.info("发生了["+message.getPayload()+"],状态从[CHOICE_AUDIT_FINISH]  变成  [WAIT_FOR_DELIVER]");
    }
    @OnTransition(source = "CHOICE_AUDIT_FINISH", target = "AUDIT_DELIVER_PARTICAL")
    public void CHOICE_AUDIT_FINISH1(Message<TradeOrderEvents> message) {
        logger.info("发生了["+message.getPayload()+"],状态从[CHOICE_AUDIT_FINISH]  变成  [AUDIT_DELIVER_PARTICAL]");
    }
    @OnTransition(source = "CHOICE_ALL_BUSINESS_SENT_OR_NOTO", target = "WAIT_FOR_SIGN")
    public void CHOICE_ALL_BUSINESS_SENT_OR_NOTO0(Message<TradeOrderEvents> message) {
        logger.info("发生了["+message.getPayload()+"],状态从[CHOICE_ALL_BUSINESS_SENT_OR_NOTO]  变成  [WAIT_FOR_SIGN]");
    }
    @OnTransition(source = "CHOICE_ALL_BUSINESS_SENT_OR_NOTO", target = "CHOICE_STORE_SENT_OR_INDEED")
    public void CHOICE_ALL_BUSINESS_SENT_OR_NOTO1(Message<TradeOrderEvents> message) {
        logger.info("发生了["+message.getPayload()+"],状态从[CHOICE_ALL_BUSINESS_SENT_OR_NOTO]  变成  [CHOICE_STORE_SENT_OR_INDEED]");
    }
    @OnTransition(source = "CHOICE_STORE_SENT_OR_INDEED", target = "WAIT_FOR_DELIVER_OTHERS")
    public void CHOICE_STORE_SENT_OR_INDEED0(Message<TradeOrderEvents> message) {
        logger.info("发生了["+message.getPayload()+"],状态从[CHOICE_STORE_SENT_OR_INDEED]  变成  [WAIT_FOR_DELIVER_OTHERS]");
    }
    @OnTransition(source = "CHOICE_STORE_SENT_OR_INDEED", target = "WAIT_FOR_DELIVER")
    public void CHOICE_STORE_SENT_OR_INDEED1(Message<TradeOrderEvents> message) {
        logger.info("发生了["+message.getPayload()+"],状态从[CHOICE_STORE_SENT_OR_INDEED]  变成  [WAIT_FOR_DELIVER]");
    }
}
