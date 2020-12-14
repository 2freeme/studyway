package com.trade.statemachine.tox;
/**
 * 可采用两种实现方式，
 * 1.适用于已研发项目，修改方法名即可：在OrderSingleEventConfig中，实现Event定义的事件（具体业务实现），保证方法名相同
 * 2.适用于架构中项目，直接实现Event中的枚举接口。
 */
public enum TradeOrderXEvents {
	CREATE_TOX,//生成派生订单
	CUSTOMER_COMPLEMENT_LINE,//客户额度补足
	AUDIT_BY_BUSINESS,//商家评审
	AUDIT_BY_AUTO,//自动评审
	BUSINESS_INTERCEPT_CONFIRM ,//商家拦截确认(影响待发数量)
	BUSINESS_SEND_CONFIRM,//商家发货确认
	BUSINESS_CANCELLED,//商家取消(可把待评全部取消完)
	CUSTOMER_INITIATED_RETURN_AND_BUSINESS_LINE_NOT_ENOUGH,//用户发起售后,并且商家额度不足
}