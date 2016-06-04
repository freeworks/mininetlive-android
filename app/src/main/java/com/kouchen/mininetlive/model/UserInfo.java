package com.kouchen.mininetlive.model;

import com.google.gson.annotations.SerializedName;
import org.parceler.Parcel;

@Parcel
public class UserInfo {

	@SerializedName("avatar")
	private String avatar;
	private String name;
	private Gender gender;
	private String note;

	public UserInfo() {
	}

	public UserInfo(String avatar, String name, Gender gender, String note) {
		this.avatar = avatar;
		this.name = name;
		this.gender = gender;
		this.note = note;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

}
