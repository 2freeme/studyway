package com.forOrder.conbine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-9-15 19:42
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CouponList {
    private Integer  couponType ;   //优惠类型
    private BigDecimal  couponAmt ;   //优惠金额
    private Integer  couponIssuer ;   //发券方
    private Long  userCouponId ;   //用户优惠券ID
    private String  couponCode ;   //优惠券编码
    private String  couponName ;   //优惠券名称
    private Integer  returnFlag ;   //是否可退：0-否；1-是
    private Integer  integralDeductQty ;   //积分抵扣数量
    private List<DivInfoList> divInfoList;

}
