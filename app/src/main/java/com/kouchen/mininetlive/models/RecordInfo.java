package com.kouchen.mininetlive.models;

import java.io.Serializable;

/**
 * Created by cainli on 16/7/2.
 */
public class RecordInfo implements Serializable{
    /**
     * aid : 12scfjfgw3d12s
     * createTime : 1988-12-07 12:07
     * title : test1
     * nickname : Hey_Cain
     * date : 1970-01-01 00:00
     */
    private String aid;
    private String createTime;
    private String title;
    private String nickname;
    private String frontCover;
    private String date;

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFrontCover() {
        return frontCover;
    }

    public void setFrontCover(String frontCover) {
        this.frontCover = frontCover;
    }
}
