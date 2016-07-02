package com.kouchen.mininetlive.activity;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.kouchen.mininetlive.MNLApplication;
import com.kouchen.mininetlive.rest.service.ActivityService;
import com.kouchen.mininetlive.rest.service.HttpResponse;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityInteractorImpl implements ActivityInteractor {

    private static final String TAG = "ActivityInteractorImpl";

    @Override
    public void getLiveList(final OnActivityFinishedListener listener) {
        final ActivityService activityService = MNLApplication.getRestClient().getActivityService();
        Call<HttpResponse> call = activityService.GetLiveList();
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                if (response.isSuccess()) {
                    HttpResponse httpResponse = response.body();
                    if (httpResponse.ret == 0) {
                        Log.i(TAG, "onResponse: " + httpResponse.data);
                        Gson gson = new Gson();
                        ActivityInfo[] activityInfos = gson.fromJson(httpResponse.data, ActivityInfo[].class);
                        listener.onSuccess(Arrays.asList(activityInfos));
                    } else {
                        listener.onError(httpResponse.msg);
                    }
                } else {
                    listener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                listener.onError("获取失败");
            }
        });
    }

    @Override
    public void getHomeList(final OnActivityFinishedListener listener) {
        final ActivityService activityService = MNLApplication.getRestClient().getActivityService();
        Call<HttpResponse> call = activityService.GetAcivityList();
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                if (response.isSuccess()) {
                    HttpResponse httpResponse = response.body();
                    if (httpResponse.ret == 0) {
                        Log.i(TAG, "onResponse: " + httpResponse.data);
                        JsonObject asJsonObject = httpResponse.data.getAsJsonObject();
                        Gson gson = new Gson();
                        HomeModel homeModel = gson.fromJson(asJsonObject, HomeModel.class);
//                        Type type = new TypeToken<Map<String, List<ActivityInfo>>>() {}.getType();
//                        gson.fromJson()
//                        Map<String, List<ActivityInfo>> data = gson.fromJson(httpResponse.data, type);
//                        HomeModel homeModel = new HomeModel();
//                        homeModel.setHasMore(data);
//                        homeModel.general = data.get("general");
//                        homeModel.recommond = data.get("recommend");
                        listener.onSuccess(homeModel);
                    } else {
                        listener.onError(httpResponse.msg);
                    }
                } else {
                    listener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                listener.onError("获取失败");
            }
        });
    }
}
