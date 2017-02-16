package com.kouchen.mininetlive.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.DecimalFormat;

/**
 * Created by cainli on 16/6/25.
 */
public class ActivityInfo implements Serializable {
    @SerializedName("aid")
    private String id;
    private String title;
    private String date;
    private String desc;
    private String frontCover;
    private int price;
    private String streamId;
    private int streamType;
    private String livePullPath;
    private String videoPath;
    private int activityState;
    private int activityType;
    private int playCount;
    private int appointmentCount;
    private int payState; //0.未开播，1.正在直播，2已经结束
    private int appoinState;
    private int onlineCount;
    @SerializedName("owner")
    private UserInfo owner;

    public ActivityInfo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getFrontCover() {
        return frontCover;
    }

    public String getFrontCover(int w, int h) {
        return frontCover + "?iopcmd=thumbnail&type=8&width=" + w + "&height=" + h;
    }

    public void setFrontCover(String frontCover) {
        this.frontCover = frontCover;
    }

    public int getPrice() {
        return price;
    }

    public String getPriceStr() {
        DecimalFormat myformat = new DecimalFormat();
        myformat.applyPattern("##,##0.00");
        return myformat.format(price / 100f);
    }


    public void setPrice(int price) {
        this.price = price;
    }

    public String getStreamId() {
        return streamId;
    }

    public void setStreamId(String streamId) {
        this.streamId = streamId;
    }

    public int getStreamType() {
        return streamType;
    }

    public void setStreamType(int streamType) {
        this.streamType = streamType;
    }

    public boolean isLiveStream() {
        return streamType == 0;
    }

    public boolean isFree() {
        return activityType == 0;
    }

    public String getLivePullPath() {
        return livePullPath;
    }

    public void setLivePullPath(String livePullPath) {
        this.livePullPath = livePullPath;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public void setVideoPath(String videoPath) {
        this.videoPath = videoPath;
    }

    public int getActivityState() {
        return activityState;
    }

    public void setActivityState(int activityState) {
        this.activityState = activityState;
    }

    public int getActivityType() {
        return activityType;
    }

    public void setActivityType(int activityType) {
        this.activityType = activityType;
    }

    public String getPlayCount() {
        DecimalFormat myformat = new DecimalFormat();
        myformat.applyPattern("##,##0");
        return myformat.format(playCount);
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public String getAppointmentCount() {
        DecimalFormat myformat = new DecimalFormat();
        myformat.applyPattern("##,##0");
        return myformat.format(appointmentCount);
    }

    public void setAppointmentCount(int appointmentCount) {
        this.appointmentCount = appointmentCount;
    }

    public int getPayState() {
        return payState;
    }

    public void setPayState(int payState) {
        this.payState = payState;
    }

    public int getAppoinState() {
        return appoinState;
    }

    public void setAppoinState(int appoinState) {
        this.appoinState = appoinState;
    }

    public UserInfo getOwner() {
        return owner;
    }

    public void setOwner(UserInfo owner) {
        this.owner = owner;
    }

    public String getOnlineCount() {
        DecimalFormat myformat = new DecimalFormat();
        myformat.applyPattern("##,##0");
        return myformat.format(onlineCount);
    }

    public void setOnlineCount(int onlineCount) {
        this.onlineCount = onlineCount;
    }

    public String getActivityStateStr() {
        switch (activityState) {
            case 0:
                return "预告";
            case 1:
                return "直播中";
            case 2:
                return "已结束";
        }
        return "";
    }

    public boolean isLiving() {
        return activityState == 1;
    }

    public boolean isAppointment() {
        return appoinState == 0;
    }

    public boolean isAppointed() {
        return appoinState > 0;
    }

    public boolean isPaid() {
        return payState > 0;
    }
}