package com.kouchen.mininetlive.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.kouchen.mininetlive.api.ActivityService;
import com.kouchen.mininetlive.contracts.HomeContract;
import com.kouchen.mininetlive.models.ActivityInfo;
import com.kouchen.mininetlive.models.HomeModel;
import com.kouchen.mininetlive.models.HttpResponse;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cainli on 16/7/14.
 */
public class HomePresenter implements HomeContract.Presenter {

    private static final String TAG = "ActivityPresenter";

    private HomeContract.View mActivityView;

    private ActivityService mActivityService;

    public HomePresenter(@NonNull ActivityService activityService,
                         @NonNull HomeContract.View authView) {
        this.mActivityView = authView;
        this.mActivityService = activityService;
    }

    @Override
    public void refresh() {
        Call<HttpResponse> call = mActivityService.GetAcivityList();
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
                        mActivityView.onLoadSuccess(homeModel);
                    } else {
                        mActivityView.onError(httpResponse.msg);
                    }
                } else {
                    mActivityView.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                mActivityView.onError("获取失败");
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    @Override
    public void initLoad() {
        Call<HttpResponse> call = mActivityService.GetAcivityList();
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                mActivityView.hideProgress();
                if (response.isSuccess()) {
                    HttpResponse httpResponse = response.body();
                    if (httpResponse.ret == 0) {
                        Log.i(TAG, "onResponse: " + httpResponse.data);
                        JsonObject asJsonObject = httpResponse.data.getAsJsonObject();
                        Gson gson = new Gson();
                        HomeModel homeModel = gson.fromJson(asJsonObject, HomeModel.class);
                        mActivityView.onLoadSuccess(homeModel);
                    } else {
                        mActivityView.onRefreshError(httpResponse.msg);
                    }
                } else {
                    mActivityView.onRefreshError(response.message());
                }
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                mActivityView.hideProgress();
                mActivityView.onRefreshError("加载数据失败");
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    @Override
    public void loadMore(String lastId) {
        Call<HttpResponse> call = mActivityService.GetAcivityListMore(lastId);
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                if (response.isSuccess()) {
                    HttpResponse httpResponse = response.body();
                    if (httpResponse.ret == 0) {
                        Log.i(TAG, "onResponse: " + httpResponse.data);
                        JsonObject asJsonObject = httpResponse.data.getAsJsonObject();
                        Gson gson = new Gson();
                        ActivityInfo[] activityInfos = gson.fromJson(asJsonObject.get("general"), ActivityInfo[].class);
                        mActivityView.onLoadMoreSuccess(Arrays.asList(activityInfos), asJsonObject.get("hasmore").getAsBoolean());
                    } else {
                        mActivityView.onLoadMoreError(httpResponse.msg);
                    }
                } else {
                    mActivityView.onLoadMoreError(response.message());
                }
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                mActivityView.onLoadMoreError("获取失败");
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

}
