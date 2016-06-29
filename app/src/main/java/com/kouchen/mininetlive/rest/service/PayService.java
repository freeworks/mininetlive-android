package com.kouchen.mininetlive.rest.service;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by cainli on 16/6/5.
 */
public interface PayService {

    @FormUrlEncoded
    @POST("/pay/charge")
    Call<HttpResponse> GetCharge(@Field("channel") String channel, @Field("amount") int amount, @Field("payType") int type);
}
