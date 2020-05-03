package com.studyway.redis.test.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * (Account)实体类
 *
 * @author makejava
 * @since 2020-04-01 15:31:36
 */
public class Account implements Serializable {
    private static final long serialVersionUID = -71245304077620271L;
    
    private Integer id;
    
    private String userName;
    
    private Double amount;
    
    private Date creatDate;
    
    private String createBy;
    
    private Date updateDate;
    
    private String updateBy;
    
    private String note;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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