package com.kouchen.mininetlive.account;

/**
 * Created by cainli on 16/7/5.
 */
public class PayRecordInfo extends RecordInfo {

    private int amount;
    private String channel;
    private int state;

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getChannel() {
        return channel;
    }

    public int getState() {
        return state;
    }

    public String getChannelString() {
        if ("wx".equals(channel)) {
            return "微信支付";
        } else if ("alipay".equals(channel)) {
            return "支付宝支付";
        }
        return "未知";
    }

    public String getStateString() {
        switch (state) {
            case 0:
                return "未开始";
            case 1:
                return "直播中";
            case 2:
                return "已结束";

        }
        return "未知";
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
