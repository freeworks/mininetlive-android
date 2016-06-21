package com.kouchen.mininetlive.rest.service;

import com.kouchen.mininetlive.model.UserInfo;
import com.kouchen.mininetlive.rest.service.base.HttpBinResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by cainli on 16/6/5.
 */
public interface ActivityService {

    @GET("/activity/detail/{id}")
    Call<HttpBinResponse> GetActivityDetail(@Path("id") String activityId);


    @GET("/activity/list")
    Call<UserInfo> GetAcivityList();

    @GET("/activity/list/more")
    Call<UserInfo> GetAcivityListMore();

}
