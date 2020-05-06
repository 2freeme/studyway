package com.studyway.redis.test.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * (Strong)实体类
 *
 * @author makejava
 * @since 2020-04-01 15:36:50
 */
public class Strong implements Serializable {
    private static final long serialVersionUID = -60371328587184799L;
    
    private Integer strongId;
    
    private String wareHouse;
    
    private String itemName;
    
    private Integer qty;
    
    private Date creatDate;
    
    private String createBy;
    
    private Date updateDate;
    
    private String updateBy;
    
    private String note;


    public Integer getStrongId() {
        return strongId;
    }

    public void setStrongId(Integer strongId) {
        this.strongId = strongId;
    }

    public String getWareHouse() {
        return wareHouse;
    }

    public void setWareHouse(String wareHouse) {
        this.wareHouse = wareHouse;
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