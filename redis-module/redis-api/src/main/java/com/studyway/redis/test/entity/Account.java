package com.studyway.redis.test.entity;

import java.math.BigDecimal;

/**
 * @Author： Dingpengfei
 * @Description：
 * @Date： 2020-3-24 17:43
 */
public class Account {
    private int accountId;
    private String userName ;

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    private BigDecimal amount;
}
