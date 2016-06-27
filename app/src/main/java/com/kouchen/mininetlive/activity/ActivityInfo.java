package com.kouchen.mininetlive.activity;

import com.google.gson.annotations.SerializedName;
import com.kouchen.mininetlive.model.UserInfo;

import java.io.Serializable;

/**
 * Created by cainli on 16/6/25.
 */
public class ActivityInfo implements Serializable {
    private int id;
    private String title;
    private String data;
    private String desc;
    @SerializedName("fontCover")
    private String frontCover;
    private String type;
    private int price;
    private String password;
    private String uid;
    private String videoId;
    private int vedioType;
    private String videoPullPath;
    private int state;//0.未开播，1.正在直播，2.可点播，3.已下线
    private int playCount;
    private String createTime;
    @SerializedName("owner")
    private UserInfo owner;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public int getVedioType() {
        return vedioType;
    }

    public void setVedioType(int vedioType) {
        this.vedioType = vedioType;
    }

    public String getVideoPullPath() {
        return videoPullPath;
    }

    public void setVideoPullPath(String videoPullPath) {
        this.videoPullPath = videoPullPath;
    }

    public int getState() {
        return state;
    }

    //0.未开播，1.正在直播，2.可点播，3.已下线
    public String getStateString() {
        switch (state) {
            case 0:
                return "未开通";
            case 1:
                return "正在直播";
            case 2:
                return "可点播";
            case 3:
                return "已下线";
        }
        return "已下线";
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getPlayCount() {
        return playCount;
    }

    public void setPlayCount(int playCount) {
        this.playCount = playCount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public UserInfo getOwner() {
        return owner;
    }

    public void setOwner(UserInfo owner) {
        this.owner = owner;
    }
}