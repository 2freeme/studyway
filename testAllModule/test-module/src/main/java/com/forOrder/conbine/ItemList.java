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
public class ItemList {
    private Integer tenantId;   //租户ID
    private String merchantCode;   //商户编码
    private String merchantName;   //商户名称
    private String storeCode;   //店铺编码
    private String storeName;   //店铺名称
    private Long itemId;   //商品ID
    private String itemCode;   //商品编码
    private String itemName;   //商品名称
    private String itemUrl;   //商品url
    private String itemSpec;   //商品规格
    private String itemSnapshot;   //商品快照（版本号用字符串）
    private Integer itemQty;   //商品数量
    private BigDecimal itemVolume;   //商品单位体积
    private BigDecimal itemGrossWeight;   //商品单位毛重
    private String unitName;   //单位名称
    private Integer orderType;   //订单类型
    private BigDecimal taxRate;   //税率
    private BigDecimal itemPriceNotax;   //未税商品价格
    private BigDecimal itemPrice;   //含税商品价格
    private BigDecimal standardPrice;   //指导价
    private BigDecimal frightPrice;   //运费价格
    private Integer giftFlag;   //是否赠品：0-否；1-是
    private Integer virtualFlag;   //是否虚拟商品：0-否；1-是
    private BigDecimal itemAmtNotax;   //未税商品金额
    private BigDecimal itemAmt;   //含税商品金额
    private BigDecimal couponAmt;   //优惠金额
    private BigDecimal freightAmt;   //运费金额
    private BigDecimal rebateAmt;   //返佣金额
    private Integer cartId;   //购物车id
    private List<PromotionList> promotionList;
 }
