//package com.base.util;
//
//import com.midea.ccs.core.enums.*;
//import com.midea.ccs.core.exception.ApplicationException;
//
//public class EnumConvertor {
//	/**
//	 * 将采购订单类型转换为政策类型
//	 * @param poOrderType 采购订单类型
//	 * @return 政策类型
//	 * @throws ApplicationException
//	 * 如果是非政策订单类型转换，抛出异常
//	 */
//	public static PolicyType poOrderType2PolicyType(PoOrderType poOrderType) {
//		switch (PoOrderType.fromValue(poOrderType.getValue())){
//			case NORMAL_PO:
//				throw new ApplicationException("不是政策订单类型");
//			case SPECIALPRICE_POLICY_PO:
//				return PolicyType.SPECIALPRICE_POLICY;
//			case PACKAGE_POLICY_PO:
//				return PolicyType.PACKAGE_POLICY;
//			case PROTOTYPE_POLICY_PO:
//				return PolicyType.PROTOTYPE_POLICY;
//			case CROWDFUNDING_POLICY_PO:
//				return PolicyType.CROWDFUNDING_POLICY;
//			case FAV_POLICY_PO:
//				return PolicyType.FAV_POLICY;
//			case PROJECT_POLICY_PO:
//				return PolicyType.PROJECT_POLICY;
//			default:
//				throw new ApplicationException("不是政策订单类型");
//		}
//	}
//
//	/**
//	 * 将采购订单类型转换成为CIMS政策订单类型
//	 * @param poOrderType 采购订单类型
//	 * @param spType 特价类型
//	 * @return CIMS政策订单类型
//	 * @throws ApplicationException
//	 * 如果是非政策订单类型转换，抛出异常
//	 */
//	public static CimsPolicyOrderType ccs2cimsPolicyOrderType(PoOrderType poOrderType, SpType spType,PkgType pkgType) {
//		switch (PoOrderType.fromValue(poOrderType.getValue())){
//			case SPECIALPRICE_POLICY_PO:
//				switch (SpType.fromValue(spType.getValue())){
//					case NORMAL:
//						return CimsPolicyOrderType.COMMON;
//					case SECKILL:
//						return CimsPolicyOrderType.COMPETE;
//				}
//			case SPECIALPRICE_WP_PO:
//				switch (SpType.fromValue(spType.getValue())){
//				case NORMAL:
//					return CimsPolicyOrderType.COMMON;
//				case SECKILL:
//					return CimsPolicyOrderType.COMPETE;
//			}
//			case FULL_REDUCTION_POLICY:
//				return CimsPolicyOrderType.FULL_MINUS;
//			case FULL_DISCOUNT_POLICY:
//				return CimsPolicyOrderType.FULL_DIS;
//			case FULL_BACK_POLICY:
//				return CimsPolicyOrderType.FULL_BACK;
//			case PACKAGE_POLICY_PO:
//				switch (PkgType.fromValue(pkgType.getValue())) {
//				case NORMAL:
//					return CimsPolicyOrderType.SET_MEAL;
//				case SECKILL:
//					return CimsPolicyOrderType.SET_MEAL_COMPETE;
//				}
//			case PACKAGE_WP_PO:
//				switch (PkgType.fromValue(pkgType.getValue())) {
//				case NORMAL:
//					return CimsPolicyOrderType.SET_MEAL;
//				case SECKILL:
//					return CimsPolicyOrderType.SET_MEAL_COMPETE;
//				}
//			case PROTOTYPE_POLICY_PO:
//				return CimsPolicyOrderType.SAMPLE;
//			case PROTOTYPE_WP_PO:
//				return CimsPolicyOrderType.SAMPLE;
//			case CROWDFUNDING_POLICY_PO:
//				return CimsPolicyOrderType.CROWD;
//			case FAV_POLICY_PO:
//				return CimsPolicyOrderType.FAV;
//			case PROJECT_POLICY_PO:
//				return CimsPolicyOrderType.PG;
//			case PROJECT_WP_POLICY:
//				return CimsPolicyOrderType.WPPG;
//			default :
//				return null;
//		}
//	}
//}
