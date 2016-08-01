package com.kouchen.mininetlive.api;

import com.kouchen.mininetlive.models.HttpResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by cainli on 16/6/5.
 */
public interface ActivityService {

    @GET("/activity/detail/{id}")
    Call<HttpResponse> GetActivityDetail(@Path("id") String activityId);

    @GET("/activity/list")
    Call<HttpResponse> GetAcivityList();

    @GET("/activity/list/more/{lastAid}")
    Call<HttpResponse> GetAcivityListMore(@Path("lastAid") String lastId);

    @GET("/activity/live/list")
    Call<HttpResponse> GetLiveList();

    @FormUrlEncoded
    @POST("/activity/leave")
    Call<HttpResponse> Leave(@Field("aid") String id);

    @FormUrlEncoded
    @POST("/activity/join")
    Call<HttpResponse> Join(@Field("aid") String id);

    @GET("/activity/member/list")
    Call<HttpResponse> GetOnlineUserList(@Query("aid") String id);

    @GET("/activity/member/count")
    Call<HttpResponse> GetOnlineUserCount(@Query("aid") String id);

    @FormUrlEncoded
    @POST("/activity/appointment")
    Call<HttpResponse> appointment(@Field("aid") String id);

}
