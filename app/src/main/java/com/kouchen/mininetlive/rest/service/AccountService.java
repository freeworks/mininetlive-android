package com.kouchen.mininetlive.rest.service;

import com.kouchen.mininetlive.rest.service.base.HttpBinResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PUT;

/**
 * Created by cainli on 16/6/5.
 */
public interface AccountService {

    @GET("/account/info")
    Call<HttpBinResponse> GetAccountInfo();

    @GET("/account/record/pay/list")
    Call<HttpBinResponse> GetPayRecordList();

    @GET("/account/record/play/list")
    Call<HttpBinResponse> GetPlayRecordList();

    @GET("/account/record/appointment/list")
    Call<HttpBinResponse> GetAppointmentRecordList();

    @PUT("/account/nickname")
    Call<HttpBinResponse> UpdateNickname();

    @GET("/account/vcode")
    Call<HttpBinResponse> GetVCode();

    @PUT("/account/phone")
    Call<HttpBinResponse> UpdatePhone();

    ///account/avatar
    @Multipart
    Call<HttpBinResponse> UpdateAvatar();


}
