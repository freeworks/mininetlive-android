package com.kouchen.mininetlive.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

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
    private int appointmentState;
    private int activityState;
    private int activityType;
    private int playCount;
    private int appointmentCount;
    private int payState; //0.未开播，1.正在直播，2已经结束
    private int appoinState;
    private String groupId;
    private String onlineCount;
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

    public void setFrontCover(String frontCover) {
        this.frontCover = frontCover;
    }

    public int getPrice() {
        return price;
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

    public boolean isLiveStream(){
        return streamType == 0;
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

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public int getAppointmentCount() {
        return appointmentCount;
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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public UserInfo getOwner() {
        return owner;
    }

    public void setOwner(UserInfo owner) {
        this.owner = owner;
    }

    public String getOnlineCount() {
        return onlineCount;
    }

    public void setOnlineCount(String onlineCount) {
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

    public int getAppointmentState() {
        return appointmentState;
    }

    public void setAppointmentState(int appointmentState) {
        this.appointmentState = appointmentState;
    }
}