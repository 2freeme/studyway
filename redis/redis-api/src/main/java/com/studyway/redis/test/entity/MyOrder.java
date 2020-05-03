package com.studyway.redis.test.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * (Myorder)实体类
 *
 * @author makejava
 * @since 2020-04-01 15:36:35
 */
public class Myorder implements Serializable {
    private static final long serialVersionUID = -40047898108781659L;
    
    private Integer orderId;
    
    private String userName;
    
    private String itemName;
    
    private Integer qty;
    
    private Date creatDate;
    
    private String createBy;
    
    private Date updateDate;
    
    private String updateBy;
    
    private String note;


    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Date getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(Date creatDate) {
        this.creatDate = creatDate;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}