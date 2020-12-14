package com.trade.statemachine.to;
/**
 * 可采用两种实现方式，
 * 1.适用于已研发项目，修改方法名即可：在OrderSingleEventConfig中，实现Event定义的事件（具体业务实现），保证方法名相同
 * 2.适用于架构中项目，直接实现Event中的枚举接口。
 */
public enum TradeOrderEvents {
	REFUNDS_SUC,//退款成功
	CUSTOMER_SUBMIT,//客户提单
	CUSTOMER_PAY,//客户付款
	CUSTOMER_CANCEL,//客户取消订单
	CUSTOMER_PAY_CANCEL,//客户取消付款
	DISPATCHER_OVERTIME,//调度超时关闭
	CLOSED,
	PAY_ALL, //全部支付
	PAY_FIRST,//支付首款
	GROUP_SUCCES,//活动结束成团
	GROUP_FAILD,//活动结束不成团
	BUSINESS_CANCELLED,//商家取消(可把待评全部取消完)
	AUDIT_BY_BUSINESS,//商家评审
	AUDIT_BY_AUTO,//自动评审
	BUSINESS_SEND_CONFIRM,//商家发货确认
	BUSINESS_INTERCEPT_CONFIRM ,//商家拦截确认(影响待发数量)
	OTHER_BUSINESS_SENT,//其他商家发货
	SIGN_CUSTOMER,//客户签收
	EVALUATE_CUSTOMER,//客户评价
	SIGN_AUTO,//15天自动签收
	EVALUATE_AUTO,//7天自动好评
}
