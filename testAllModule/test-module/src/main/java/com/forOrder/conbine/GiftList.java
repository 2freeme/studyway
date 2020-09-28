package com.forOrder.conbine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-9-15 20:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GiftList {
    private Integer  giftId ;   //赠品ID
    private String  giftCode ;   //赠品编码
    private BigDecimal standardPrice ;   //标准价格
    private Integer  giftQty ;   //赠品数量
}
