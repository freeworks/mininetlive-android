package com.kouchen.mininetlive.api;

import com.kouchen.mininetlive.models.HttpResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by cainli on 16/6/5.
 */
public interface PayService {

    @FormUrlEncoded
    @POST("/pay/charge")
    Call<HttpResponse> GetCharge(@Field("aid") String aid, @Field("channel") String channel, @Field("amount") int amount, @Field("payType") int type);

    @FormUrlEncoded
    @POST("/pay/transfer")
    Call<HttpResponse> Withdraw(@Field("amount") int amount);

    @GET("/account/balance")
    Call<HttpResponse> getBalance();
}
