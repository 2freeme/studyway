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
 * @Date： 2020-9-15 19:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Content {
    private String  userPhone ;   //用户手机号
    private Integer  sumQty ;   //合计数量
    private BigDecimal  sumVolume ;   //合计体积
    private BigDecimal  sumGrossWeight ;   //合计毛重
    private BigDecimal  sumItemAmtNotax ;   //未税商品总金额
    private BigDecimal  sumItemAmt ;   //含税商品总金额
    private BigDecimal sumCouponAmt ;   //优惠券总金额
    private BigDecimal  sumFreightAmt ;   //运费金额
    private BigDecimal  sumRebateAmt ;   //返佣金额
    private BigDecimal  sumApAmt ;   //应付合计金额(扣减优惠券后)
    private BigDecimal  sumPaidAmt ;   //实付总金额
    private String  currency ;   //币种
    private BigDecimal  exchangeRate ;   //汇率
    private Integer  integral ;   //赠送积分
    private Integer  expiryMin ;   //过期时间（分）
    private Integer  payType ;   //支付方式：1-在线支付；2-到店付款；3-货到付款
    private Integer  shipType ;   //配送方式：1、自提；2-配送；3-到店自提
    private Integer  invoiceFlag ;   //是否需要开票：0-否；1-是
    private String  remark ;   //备注
    private List<ItemList> itemList;
    private List<CmsInfoHead> cmsInfoHead;
    private List<CouponList> couponList;
    private List<InvoiceInfo> invoiceInfo;
   // private List<ReceiverInfo> receiverInfo;
}
