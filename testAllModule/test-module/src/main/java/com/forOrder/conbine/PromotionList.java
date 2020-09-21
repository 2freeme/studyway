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
 * @Date： 2020-9-15 19:59
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PromotionList {
    private Integer  promotionIssuer ;   //促销承担方
    private Integer  promotionType ;   //促销类型
    private String  promotionCode ;   //活动编码
    private String  promotionName ;   //活动名称
    private BigDecimal promotionAmt ;   //活动优惠金额
    private List<GiftList> giftList;
}
