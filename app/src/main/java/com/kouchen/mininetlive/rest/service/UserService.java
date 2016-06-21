package com.kouchen.mininetlive.rest.service;


import com.kouchen.mininetlive.rest.service.base.HttpBinResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by cainli on 16/6/21.
 */
public interface UserService {


    @GET("/user/info/{id}")
    Call<HttpBinResponse> GetUserInfo(@Path("id") String id);
}
