package com.kouchen.mininetlive.rest.service;

import com.kouchen.mininetlive.model.UserInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by cainli on 16/6/4.
 */
public interface AccountService  {

    class LoginData {
        String username;
        String password;

        public LoginData(String username, String password) {
            this.username = username;
            this.password = password;
        }
    }

    @GET("/user/login")
    Call<HttpBinResponse> login(@Query("plat") String plat, @Query("username") String username,
        @Query("password") String password);

    @GET("/user/login")
    Call<HttpBinResponse> login2(@Query("plat") String plat, @Query("openId") String openId,
        @Query("access_token") String accessToken, @Query("expires_in") long expiresIn);

    @POST("/user/register")
    void register();

    @GET("/user/{userId}")
    Call<UserInfo> getUserInfo(@Path("userId") String userId);

    @GET("/user/list/{activityId}")
    Call<UserInfo> getUserListByActivityId(@Path("activityId") String activityId);

}