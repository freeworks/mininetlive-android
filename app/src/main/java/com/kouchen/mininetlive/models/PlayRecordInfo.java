package com.kouchen.mininetlive.models;

import com.kouchen.mininetlive.models.RecordInfo;

import java.text.DecimalFormat;

/**
 * Created by cainli on 16/7/5.
 */
public class PlayRecordInfo extends RecordInfo {

    private long playCount;

    private String channel;

    public String getPlayCount() {
        DecimalFormat myformat = new DecimalFormat();
        myformat.applyPattern("##,##0" );
        return myformat.format(playCount);
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
