package com.kouchen.mininetlive.rest.service;

import com.kouchen.mininetlive.model.UserInfo;

import retrofit2.Call;
import retrofit2.http.POST;

/**
 * Created by cainli on 16/6/21.
 */
public interface CommonService {

    @POST("/common/inviteCode")
    Call<UserInfo> commitInviteCode();
}