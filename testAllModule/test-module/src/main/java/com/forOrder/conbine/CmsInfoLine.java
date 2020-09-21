package com.forOrder.conbine;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-9-15 20:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CmsInfoLine {
    private Integer  shopId ;   //CMS门店ID
    private Long  itemId ;   //CMS商品ID
    private Integer  virtualShopFlag ;   //是否虚拟门店：0-否；1-是
}
