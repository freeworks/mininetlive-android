package com.kouchen.mininetlive.account;

/**
 * Created by cainli on 16/7/5.
 */
public class PayRecordInfo extends RecordInfo{

    private int amount;
    private int orderType;

    public int getOrderType() {
        return orderType;
    }

    public void setOrderType(int orderType) {
        this.orderType = orderType;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
