package com.studyway.redis.test.entity;

import java.math.BigDecimal;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-3-24 17:42
 */
public class Item {
    private int itemId;
    private String itemName ;
    private BigDecimal price;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
