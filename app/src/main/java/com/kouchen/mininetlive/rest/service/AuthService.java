package com.kouchen.mininetlive.rest.service;

import com.kouchen.mininetlive.model.UserInfo;
import com.kouchen.mininetlive.rest.service.base.HttpBinResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by cainli on 16/6/4.
 */
public interface AuthService {


    @POST("/auth/login")
    Call<HttpBinResponse> login(@Query("username") String username, @Query("password") String password);

    @POST("/auth/register")
    Call<HttpBinResponse> register();

    @POST("/oauth/login")
    Call<HttpBinResponse> oauthLogin(@Query("openId") String type,@Query("openId") String openId,@Query("access_token") String accessToken,@Query("expires_in") long expiresIn);

    @POST("/oauth/register")
    Call<HttpBinResponse> oauthRegister();

    @POST("/auth/logout")
    Call<HttpBinResponse> logout();

}