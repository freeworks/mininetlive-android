package com.kouchen.mininetlive.model;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

import java.io.Serializable;

@Parcel
public class UserInfo implements Serializable{

	@SerializedName("avatar")
	public String avatar;
	public String nickname;
	public Gender gender;
	public String note;
	public String uid;
	public String easemobUuid;
	public int balance;
	public String inviteCode;
	public String qrCode;
	public String phone;

	public UserInfo() {
	}

	public UserInfo(String avatar, String nickname, Gender gender, String note) {
		this.avatar = avatar;
		this.nickname = nickname;
		this.gender = gender;
		this.note = note;
	}

	public String getAvatar() {
		return avatar;
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

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public static enum Gender {MALE, FEMALE}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getEasemobUuid() {
		return easemobUuid;
	}

	public void setEasemobUuid(String easemobUuid) {
		this.easemobUuid = easemobUuid;
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

	public String getQrCode() {
		return qrCode;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
}
