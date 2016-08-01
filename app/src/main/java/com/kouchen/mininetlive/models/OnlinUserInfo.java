package com.kouchen.mininetlive.models;

import java.io.Serializable;

/**
 * Created by cainli on 16/8/1.
 */
public class OnlinUserInfo implements Serializable {

    private String uid;
    private String avatar;
    private String nickname;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAvatar() {
        return avatar+"?iopcmd=thumbnail&type=8&width=50&height=50";
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
