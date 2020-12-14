package com.trade.statemachine.tox;

public enum TradeOrderXStates {
	CREATE,
	WAIT_FOR_PAY,//待付款
	WAIT_FOR_AUDIT,//待评审
	CHOICE_ALL_CANCELLED,//是否全部取消,无派生TO
	CLOSED,//订单关闭
	CHOICE_AUDIT_FINISH,//是否评审完毕
	AUDIT_DELIVER_PARTICAL,//部分评审发货(部分评审,货未发完)
	WAIT_FOR_DELIVER,//	待发货
	CHOICE_ALL_BUSINESS_SENT_OR_NOTO,//是否发货完毕或无需发货
	CHOICE_NUM_OF_SEND_GT0,//待发>0
	COMPLETED; //订单完成
}