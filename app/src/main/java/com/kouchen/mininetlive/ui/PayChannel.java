package com.kouchen.mininetlive.ui;

/**
 * Created by cainli on 16/6/21.
 */
public enum PayChannel {

    CHANNEL_WECHAT("wx"),
    CHANNEL_ALIPAY("alipay");

    private String channelName;

    PayChannel(String channelName) {
        this.channelName = channelName;

    }

    public String getChannelName() {
        return channelName;
    }
}
