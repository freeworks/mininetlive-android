package com.kouchen.mininetlive.rest.service;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by cainli on 16/6/5.
 */
public interface ActivityService {

    @GET("/activity/detail/{id}")
    Call<HttpResponse> GetActivityDetail(@Path("id") String activityId);

    @GET("/activity/list")
    Call<HttpResponse> GetAcivityList();

    @GET("/activity/list/more")
    Call<HttpResponse> GetAcivityListMore();

    @GET("/activity/live/list")
    Call<HttpResponse> GetLiveList();

}
