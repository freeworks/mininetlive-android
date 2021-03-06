package com.kouchen.mininetlive.api;

import com.kouchen.mininetlive.models.HttpResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

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

    @GET("/account/record/transfer/list")
    Call<HttpResponse> GetWithdrawRecordList();

    @GET("/account/record/dividend/list")
    Call<HttpResponse> GetDividendRecordList();

    @FormUrlEncoded
    @POST("/account/nickname")
    Call<HttpResponse> updateNickname(@Field("nickname") String name);

    @FormUrlEncoded
    @POST("/account/vcode")
    Call<HttpResponse> getVCode(@Field("phone") String phone);

    @FormUrlEncoded
    @POST("/account/phone")
    Call<HttpResponse> updatePhone(@Field("phone") String name,@Field("vcode") String vcode);

    @Multipart
    @POST("/account/avatar")
    Call<HttpResponse> uploadAvatar(@PartMap Map<String, RequestBody> params);
}
