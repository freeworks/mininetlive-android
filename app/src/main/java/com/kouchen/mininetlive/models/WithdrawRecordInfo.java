package com.kouchen.mininetlive.models;

import java.io.Serializable;

/**
 * Created by cainli on 16/7/2.
 */
public class WithdrawRecordInfo implements Serializable {

    private int amount;
    private String createTime;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
