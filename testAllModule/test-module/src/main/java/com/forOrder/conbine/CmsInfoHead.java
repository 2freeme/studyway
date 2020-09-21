package com.forOrder.conbine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-9-15 19:58
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CmsInfoHead {
    private Integer  partnerId ;   //合伙人ID
    private String  partnerName ;   //合伙人姓名
    private String  checkCode ;   //用于大屏端校验订单支付状态的字段
    private String  customerMobile ;   //顾客移动电话
    private String  customerTel ;   //顾客固定电话

}
