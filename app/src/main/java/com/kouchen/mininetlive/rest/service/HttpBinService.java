package com.kouchen.mininetlive.rest.service;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by cainli on 16/6/5.
 */
public interface HttpBinService {

    @GET("/get")
    Call<HttpBinResponse> get();

    // request /get?testArg=...
    @GET("/get")
    Call<HttpBinResponse> getWithArg(@Query("testArg") String arg
    );

    // POST form encoded with form field params
    @FormUrlEncoded
    @POST("/post")
    Call<HttpBinResponse> postWithFormParams(@Field("field1") String field1
    );

}
