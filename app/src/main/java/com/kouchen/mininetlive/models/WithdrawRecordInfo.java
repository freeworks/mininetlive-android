package com.kouchen.mininetlive.models;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * Created by cainli on 16/7/2.
 */
public class WithdrawRecordInfo implements Serializable {

    private int amount;
    private String createTime;

    public String getAmount() {
        DecimalFormat myformat = new DecimalFormat();
        myformat.applyPattern("##,##0.00" );
        return myformat.format(amount);
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
