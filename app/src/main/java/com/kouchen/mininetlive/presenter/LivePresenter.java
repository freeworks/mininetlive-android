package com.kouchen.mininetlive.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.kouchen.mininetlive.api.ActivityService;
import com.kouchen.mininetlive.contracts.LiveContract;
import com.kouchen.mininetlive.models.ActivityInfo;
import com.kouchen.mininetlive.models.HttpResponse;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cainli on 16/7/14.
 */
public class LivePresenter implements LiveContract.Presenter {

    private static final String TAG = "LivePresenter";

    private LiveContract.View mActivityView;

    private ActivityService mActivityService;

    public LivePresenter(@NonNull ActivityService activityService,
                         @NonNull LiveContract.View authView) {
        this.mActivityView = authView;
        this.mActivityService = activityService;
    }

    @Override
    public void refresh() {
        Call<HttpResponse> call = mActivityService.GetLiveList();
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                if (response.isSuccess()) {
                    HttpResponse httpResponse = response.body();
                    if (httpResponse.ret == 0) {
                        Log.i(TAG, "onResponse: " + httpResponse.data);
                        Gson gson = new Gson();
                        ActivityInfo[] activityInfos = gson.fromJson(httpResponse.data, ActivityInfo[].class);
                        mActivityView.onLoadSuccess(Arrays.asList(activityInfos));
                    } else {
                        mActivityView.onRefreshError(httpResponse.msg);
                    }
                } else {
                    mActivityView.onRefreshError(response.message());
                }
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                mActivityView.onRefreshError("获取失败");
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    @Override
    public void initLoad() {
        Call<HttpResponse> call = mActivityService.GetLiveList();
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                mActivityView.hideProgress();
                if (response.isSuccess()) {
                    HttpResponse httpResponse = response.body();
                    if (httpResponse.ret == 0) {
                        Log.i(TAG, "onResponse: " + httpResponse.data);
                        Gson gson = new Gson();
                        ActivityInfo[] activityInfos = gson.fromJson(httpResponse.data, ActivityInfo[].class);
                        mActivityView.onLoadSuccess(Arrays.asList(activityInfos));
                    } else {
                        mActivityView.onInitLoadError(httpResponse.msg);
                    }
                } else {
                    mActivityView.onInitLoadError(response.message());
                }
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                mActivityView.hideProgress();
                mActivityView.onInitLoadError("获取失败");
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }
}
