package com.kouchen.mininetlive.api;

import com.kouchen.mininetlive.models.HttpResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

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
                                @Field("inviteCode") String inviteCode,
                                @Field("nickname")String nickname,
                                @Field("gender")int gender);

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

    @FormUrlEncoded
    @POST("/auth/verify/phone")
    Call<HttpResponse> checkPhone(@Field("phone")String phone, @Field("vcode")String vcode);


    @FormUrlEncoded
    @POST("/common/inviteCode")
    Call<HttpResponse> postInviteCode(@Field("inviteCode")String inviteCode);


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