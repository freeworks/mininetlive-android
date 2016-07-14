package com.kouchen.mininetlive.api;

import com.kouchen.mininetlive.models.HttpResponse;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PUT;
import retrofit2.http.Part;

/**
 * Created by cainli on 16/6/5.
 */
public interface AccountService {

    @GET("/account/info")
    Call<HttpResponse> GetAccountInfo();

    @GET("/account/record/pay/list")
    Call<HttpResponse> GetPayRecordList();

    @GET("/account/record/play/list")
    Call<HttpResponse> GetPlayRecordList();

    @GET("/account/record/appointment/list")
    Call<HttpResponse> GetAppointmentRecordList();

    @GET("/account/record/withdraw/list")
    Call<HttpResponse> GetWithdrawRecordList();

    @PUT("/account/nickname")
    Call<HttpResponse> UpdateNickname();

    @GET("/account/vcode")
    Call<HttpResponse> GetVCode();

    @PUT("/account/phone")
    Call<HttpResponse> UpdatePhone();

    @Multipart
    @PUT("account/avatar")
    Call<HttpResponse> UpdateAvatar(@Part("photo") RequestBody photo, @Part("uid") String uid);


}
