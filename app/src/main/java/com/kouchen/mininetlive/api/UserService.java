package com.kouchen.mininetlive.api;


import com.kouchen.mininetlive.models.HttpResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by cainli on 16/6/21.
 */
public interface UserService {


    @GET("/user/info/{id}")
    Call<HttpResponse> GetUserInfo(@Path("id") String id);
}
