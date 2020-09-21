package com.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-9-15 19:09
 */
public class JsonTest {
    public static void main(String[] args) {
submitOrder();
    }

    public static void submitOrder(){
        JSONObject a = new JSONObject();
        JSONObject header = new JSONObject();
        JSONObject content = new JSONObject();
        header.put("sourceSys","来源系统-cms");
        header.put("sourceSubsystem","系统来源,ym-易买,dp-大屏,yjy-研究院,xcx-小程序");
        header.put("userCode","用户编码");
        header.put("userName","用户姓名");

        content.put("userPhone","用户手机号");
        content.put("sumQty","合计数量");
        content.put("sumVolume","合计体积");
        content.put("sumGrossWeight","合计毛重");
        content.put("sumItemAmtNotax","未税商品总金额");
        content.put("sumItemAmt","含税商品总金额");
        content.put("sumCouponAmt","优惠券总金额");
        content.put("sumFreightAmt","运费金额");
        content.put("sumRebateAmt","返佣金额");
        content.put("sumApAmt","应付合计金额(扣减优惠券后)");
        content.put("sumPaidAmt","实付总金额");
        content.put("currency","币种");
        content.put("exchangeRate","汇率");
        content.put("integral","赠送积分");
        content.put("expiryMin","过期时间（分）");
        content.put("payType","支付方式：1-在线支付；2-到店付款；3-货到付款");
        content.put("shipType","配送方式：1、自提；2-配送；3-到店自提");
        content.put("invoiceFlag","是否需要开票：0-否；1-是");
        content.put("remark","备注");

        JSONObject receiverInfo = new JSONObject();
        
        receiverInfo.put("receiverName","收货人姓名");
        receiverInfo.put("receiverPhone","收货人移动电话");
        receiverInfo.put("receiverTel","收货人固定电话");
        receiverInfo.put("lv0putressCode","国家ID");
        receiverInfo.put("lv1putressCode","省");
        receiverInfo.put("lv2putressCode","市");
        receiverInfo.put("lv3putressCode","县");
        receiverInfo.put("lv4putressCode","镇");
        receiverInfo.put("customputress","详细地址");
        receiverInfo.put("zipCode","邮政编码");
        receiverInfo.put("deliveryBookTime","预约配送时间");
        receiverInfo.put("installBookTime","预约安装时间");

        JSONObject invoiceInfo = new JSONObject();
        invoiceInfo.put("tenantId","租户ID");
        invoiceInfo.put("merchantCode","商户编码");
        invoiceInfo.put("invoiceType","发票类型：1-普票；2-专票");
        invoiceInfo.put("invoiceTitleType","发票抬头类型：1-个人；2-单位");
        invoiceInfo.put("invoiceMedia","发票介质：1-电子；2-纸质");
        invoiceInfo.put("invoiceTitle","发票抬头");
        invoiceInfo.put("invoiceContent","发票内容");
        invoiceInfo.put("invoiceTaxNumber","纳税人识别号");
        invoiceInfo.put("taxpayersBank","开户银行");
        invoiceInfo.put("bankAccountNumber","开户银行账号");
        invoiceInfo.put("taxpayersputress","企业地址");
        invoiceInfo.put("taxpayersPhone","企业电话");
        invoiceInfo.put("recInvoicePhone","收票人电话");
        invoiceInfo.put("recInvoiceName","收票人名称");
        invoiceInfo.put("lv0putressCode","国家ID");
        invoiceInfo.put("lv1putressCode","收票人-省");
        invoiceInfo.put("lv2putressCode","收票人-市");
        invoiceInfo.put("lv3putressCode","收票人-县");
        invoiceInfo.put("lv4putressCode","收票人-镇");
        invoiceInfo.put("customputress","收票人-详细地址");
        invoiceInfo.put("zipCode","邮政编码");
        invoiceInfo.put("email","电子邮箱");

        JSONObject couponList = new JSONObject();
        couponList.put("couponType","优惠类型");
        couponList.put("couponAmt","优惠金额");
        couponList.put("couponIssuer","发券方");
        couponList.put("userCouponId","用户优惠券ID");
        couponList.put("couponCode","优惠券编码");
        couponList.put("couponName","优惠券名称");
        couponList.put("returnFlag","是否可退：0-否；1-是");
        couponList.put("integralDeductQty","积分抵扣数量");


        JSONObject divInfoList = new JSONObject();
        divInfoList.put("divType","摊分类型");
        divInfoList.put("itemId","商品id");
        divInfoList.put("itemCode","商品编码");
        divInfoList.put("itemName","商品名称");
        divInfoList.put("divAmt","摊分金额");


        JSONObject cmsInfo = new JSONObject();
        cmsInfo.put("partnerId","合伙人ID");
        cmsInfo.put("partnerName","合伙人姓名");
        cmsInfo.put("checkCode","用于大屏端校验订单支付状态的字段");
        cmsInfo.put("customerMobile","顾客移动电话");
        cmsInfo.put("customerTel","顾客固定电话");



        JSONObject cmsInfoLine = new JSONObject();
        cmsInfoLine.put("shopId","CMS门店ID");
        cmsInfoLine.put("itemId","CMS商品ID");
        cmsInfoLine.put("virtualShopFlag","是否虚拟门店：0-否；1-是");

        JSONObject itemList = new JSONObject();
        itemList.put("tenantId","租户ID");
        itemList.put("merchantCode","商户编码");
        itemList.put("merchantName","商户名称");
        itemList.put("storeCode","店铺编码");
        itemList.put("storeName","店铺名称");
        itemList.put("itemId","商品ID");
        itemList.put("itemCode","商品编码");
        itemList.put("itemName","商品名称");
        itemList.put("itemUrl","商品url");
        itemList.put("itemSpec","商品规格");
        itemList.put("itemSnapshot","商品快照（版本号用字符串）");
        itemList.put("itemQty","商品数量");
        itemList.put("itemVolume","商品单位体积");
        itemList.put("itemGrossWeight","商品单位毛重");
        itemList.put("unitName","单位名称");
        itemList.put("orderType","订单类型");
        itemList.put("taxRate","税率");
        itemList.put("itemPriceNotax","未税商品价格");
        itemList.put("itemPrice","含税商品价格");
        itemList.put("standardPrice","指导价");
        itemList.put("frightPrice","运费价格");
        itemList.put("giftFlag","是否赠品：0-否；1-是");
        itemList.put("virtualFlag","是否虚拟商品：0-否；1-是");
        itemList.put("itemAmtNotax","未税商品金额");
        itemList.put("itemAmt","含税商品金额");
        itemList.put("couponAmt","优惠金额");
        itemList.put("freightAmt","运费金额");
        itemList.put("rebateAmt","返佣金额");
        itemList.put("cartId","购物车id");
        itemList.put("cmsInfoLine",cmsInfoLine);



        JSONObject giftList = new JSONObject();
        giftList.put("giftId","赠品ID");
        giftList.put("giftCode","赠品编码");
        giftList.put("standardPrice","标准价格");
        giftList.put("giftQty","赠品数量");

        JSONObject promotionList = new JSONObject();
        promotionList.put("promotionIssuer","促销承担方");
        promotionList.put("promotionType","促销类型");
        promotionList.put("promotionCode","活动编码");
        promotionList.put("promotionName","活动名称");
        promotionList.put("promotionAmt","活动优惠金额");
        JSONArray  giftLists = new JSONArray();
        giftLists.add(giftList);
        promotionList.put("giftList",giftLists);



        JSONArray  promotionLists = new JSONArray();
        promotionLists.add(promotionList);
        itemList.put("promotionList",promotionLists);


        JSONArray  itemLists = new JSONArray();
        itemLists.add(itemList);
        content.put("itemList",itemLists);


        JSONArray  divInfoLists = new JSONArray();
        divInfoLists.add(divInfoList);
        couponList.put("divInfoList",divInfoLists);


//        JSONArray  couponLists = new JSONArray();
//        couponLists.add(couponList);
//        couponList.put("couponList",couponLists);



        content.put("cmsInfo",cmsInfo);
        content.put("couponList",couponList);
        content.put("invoiceInfo",invoiceInfo);
        content.put("receiverInfo",receiverInfo);


        a.put("header",header);
        a.put("content",content);
        System.out.println("=================");
        System.out.println(a.toString());











    }

}
