package com.kouchen.mininetlive.api;

import com.kouchen.mininetlive.models.HttpResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

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
    @POST("/activity/group/leave")
    Call<HttpResponse> Leave(@Field("groupId") String groupId);

    @FormUrlEncoded
    @POST("/activity/group/join")
    Call<HttpResponse> Join(@Field("groupId") String groupId);

    @FormUrlEncoded
    @POST("/activity/group/member/list")
    Call<HttpResponse> GetGroupOnlineUserList(@Field("groupId") String groupId);

    @FormUrlEncoded
    @POST("/activity/group/member/count")
    Call<HttpResponse> GetGroupOnlineUserCount(@Field("groupId") String groupId);

}
