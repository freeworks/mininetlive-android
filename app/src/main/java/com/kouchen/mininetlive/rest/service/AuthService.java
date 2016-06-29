package com.kouchen.mininetlive.rest.service;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by cainli on 16/6/4.
 */
public interface AuthService {


    @FormUrlEncoded
    @POST("/auth/login")
    Call<HttpResponse> login(@Field("phone") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("/auth/vcode")
    Call<HttpResponse> getVCode(@Field("phone") String phone);

    @FormUrlEncoded
    @POST("/auth/register")
    Call<HttpResponse> register(@Field("phone") String phone,
                                @Field("vode") String vcode,
                                @Field("password") String password,
                                @Field("inviteCode") String inviteCode);

    @FormUrlEncoded
    @POST("/oauth/login")
    Call<HttpResponse> oauthLogin(@Field("plat") String plat, @Field("openid") String openId, @Field("access_token") String accessToken, @Field("expires_in") long expiresIn);

    @FormUrlEncoded
    @POST("/oauth/register")
    Call<HttpResponse> oauthRegister(@Field("plat") String plat, @Field("openid") String userId,
                                     @Field("access_token") String token,
                                     @Field("expires_in") long expiresIn,
                                     @Field("city") String city,
                                     @Field("nickname") String nickname,
                                     @Field("gender") int gender,
                                     @Field("avatar") String avatar);

    @FormUrlEncoded
    @POST("/auth/logout")
    Call<HttpResponse> logout();


//    // POST form encoded with form field params
//    @FormUrlEncoded
//    @POST("/post")
//    Call<HttpResponse> postWithFormParams(@Field("field1") String field1
//    );
// request /get?testArg=...
//@GET("/get")
//Call<HttpResponse> getWithArg(@Field("testArg") String arg
//);

}