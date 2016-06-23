package com.kouchen.mininetlive.rest.service;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by cainli on 16/6/4.
 */
public interface AuthService {


    @POST("/auth/login")
    Call<HttpResponse> login(@Query("phone") String username, @Query("password") String password);

    @POST("/auth/register")
    Call<HttpResponse> register();

    @POST("/oauth/login")
    Call<HttpResponse> oauthLogin(@Query("plat") String type, @Query("openid") String openId, @Query("access_token") String accessToken, @Query("expires_in") long expiresIn);

    @POST("/oauth/register")
    Call<HttpResponse> oauthRegister();

    @POST("/auth/logout")
    Call<HttpResponse> logout();



//    // POST form encoded with form field params
//    @FormUrlEncoded
//    @POST("/post")
//    Call<HttpResponse> postWithFormParams(@Field("field1") String field1
//    );
// request /get?testArg=...
//@GET("/get")
//Call<HttpResponse> getWithArg(@Query("testArg") String arg
//);

}