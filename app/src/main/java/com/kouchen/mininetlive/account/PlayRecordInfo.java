package com.kouchen.mininetlive.account;

/**
 * Created by cainli on 16/7/5.
 */
public class PlayRecordInfo extends RecordInfo {

    private long playCount;

    private String channel;


    public long getPlayCount() {
        return playCount;
    }

    public void setPlayCount(long playCount) {
        this.playCount = playCount;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
