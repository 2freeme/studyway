package com.forOrder.conbine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-9-15 19:40
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InvoiceInfo {
    private Integer  tenantId ;   //租户ID
    private String  merchantCode ;   //商户编码
    private Integer  invoiceType ;   //发票类型：1-普票；2-专票
    private Integer  invoiceTitleType ;   //发票抬头类型：1-个人；2-单位
    private Integer  invoiceMedia ;   //发票介质：1-电子；2-纸质
    private String  invoiceTitle ;   //发票抬头
    private String  invoiceContent ;   //发票内容
    private String  invoiceTaxNumber ;   //纳税人识别号
    private String  taxpayersBank ;   //开户银行
    private String  bankAccountNumber ;   //开户银行账号
    private String  taxpayersAddress ;   //企业地址
    private String  taxpayersPhone ;   //企业电话
    private String  recInvoicePhone ;   //收票人电话
    private String  recInvoiceName ;   //收票人名称
    private String  lv0AddressCode ;   //国家ID
    private String  lv1AddressCode ;   //收票人-省
    private String  lv2AddressCode ;   //收票人-市
    private String  lv3AddressCode ;   //收票人-县
    private String  lv4AddressCode ;   //收票人-镇
    private String  customAddress ;   //收票人-详细地址
    private String  zipCode ;   //邮政编码
    private String  email ;   //电子邮箱
}
