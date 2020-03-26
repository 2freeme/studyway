package com.studyway.redis.test.entity;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-3-24 17:46
 */
public class Strong {
    int StrongId;
    private String wareHouse;
    private int qty;

    public int getStrongId() {
        return StrongId;
    }

    public void setStrongId(int strongId) {
        StrongId = strongId;
    }

    public String getWareHouse() {
        return wareHouse;
    }

    public void setWareHouse(String wareHouse) {
        this.wareHouse = wareHouse;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Strong(int strongId, String wareHouse, int qty, String itemName) {
        StrongId = strongId;
        this.wareHouse = wareHouse;
        this.qty = qty;
        this.itemName = itemName;
    }

    public Strong() {
    }

    private String itemName;
}
