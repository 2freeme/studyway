package com.forOrder.conbine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-9-15 19:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DivInfoList {
    private Integer  divType ;   //摊分类型
    private Long  itemId ;   //商品id
    private String  itemCode ;   //商品编码
    private String  itemName ;   //商品名称
    private BigDecimal  divAmt ;   //摊分金额

}
