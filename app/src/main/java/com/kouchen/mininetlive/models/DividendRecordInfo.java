package com.kouchen.mininetlive.models;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * Created by cainli on 16/7/2.
 */
public class DividendRecordInfo implements Serializable {

    private int amount;
    private String createTime;
    private String nickname;

    public String getAmount() {
        DecimalFormat myformat = new DecimalFormat();
        myformat.applyPattern("##,##0.00");
        return myformat.format(amount/100f);
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
