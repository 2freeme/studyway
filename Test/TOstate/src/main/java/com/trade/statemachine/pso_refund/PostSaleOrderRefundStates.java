package com.trade.statemachine.pso_refund;

public enum PostSaleOrderRefundStates {
	CREATE,
	WAIT_FOR_AUDIT,//待评审
	AUDIT_PARTICALLY,//部分评审
	WAIT_FOR_RETURN,//待退货
	WAIT_FOR_REFOUND,//待退款
	CLOSED,//订单关闭9
	COMPLETED, //订单完成
	BUSINESS_AUDIT,//商家评审
	CHOICE_AUDIT_ALL,//TODO 待评审经过商家全部评审到完成; 部分评审经过商家评审到完成|关闭
}