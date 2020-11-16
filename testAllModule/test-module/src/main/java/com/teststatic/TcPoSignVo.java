package com.teststatic;

import lombok.Data;

import java.util.List;

/**
 * @Author：
 * @Description：
 * @Date： 2020-11-5 14:04
 */
@Data
public class TcPoSignVo {
    private String poNo;
    private List<SignItem> itemList;

    @Data
    public static class SignItem{
        private String itemCode;
        private Integer billQty;
        private Integer shareAreaQty;
        private Integer storeQty;
        private List<StoreItem> storeItemList;

    }
    @Data
    public static class StoreItem{
        private Long shopId;
        private Integer qty;
    }
}
