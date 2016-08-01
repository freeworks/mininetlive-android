package com.kouchen.mininetlive.models;

import java.io.Serializable;

/**
 * Created by cainli on 16/7/2.
 */
public class UserInfo implements Serializable {
    /**
     * uid : 9bad59c851afbaed
     * easemobUuid : c36f5d4a-3c29-11e6-824f-a92ee3ae814e
     * nickname : ttttttt
     * avatar : http://www.baidu.com
     * gender : 1
     * balance : 0
     * inviteCode : 642484
     * qrcode : http://h.hiphotos.baidu.com/image/pic/item/3bf33a87e950352a5936aa0a5543fbf2b2118b59.jpg
     * phone : 18689490100
     */
    private String uid;
    private String nickname;
    private String avatar;
    private int gender;
    private int balance;
    private String inviteCode;
    private String qrcode;
    private String phone;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar+"?iopcmd=thumbnail&type=8&width=200&height=200";
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getInviteCode() {
        return inviteCode;
    }

    public void setInviteCode(String inviteCode) {
        this.inviteCode = inviteCode;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
