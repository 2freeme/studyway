package com.trade.statemachine.pso_refund;
/**
 * 可采用两种实现方式，
 * 1.适用于已研发项目，修改方法名即可：在OrderSingleEventConfig中，实现Event定义的事件（具体业务实现），保证方法名相同
 * 2.适用于架构中项目，直接实现Event中的枚举接口。
 */
public enum PostSaleOrderRefundEvents {
	CUSTOMER_INITIATED_RETURN,//客户发起退货申请
	CUSTOMER_CANCELLED,//客户取消
	BUSINESS_CANCELLED,//商家驳回关闭
	BUSINESS_AUDIT,
	CONFIRM_REFUND_INBOUND,//退货入库确认
}