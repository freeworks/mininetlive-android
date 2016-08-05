package com.kouchen.mininetlive.presenter;

import android.support.annotation.NonNull;
import android.util.Log;
import com.google.gson.Gson;
import com.kouchen.mininetlive.api.ActivityService;
import com.kouchen.mininetlive.api.PayService;
import com.kouchen.mininetlive.contracts.ActivityDetailContract;
import com.kouchen.mininetlive.models.HttpResponse;
import com.kouchen.mininetlive.models.OnlinUserInfo;
import com.kouchen.mininetlive.ui.PayChannel;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by cainli on 16/6/25.
 */
public class ActivityDetailPresenter implements ActivityDetailContract.Presenter{

    private static final String TAG = "PayPresenter";

    private ActivityDetailContract.View mView;

    private PayService mPayService;

    private ActivityService mActivityService;

    public ActivityDetailPresenter(@NonNull PayService payService,@NonNull ActivityService activityService,@NonNull ActivityDetailContract.View view) {
        this.mView = view;
        this.mActivityService = activityService;
        this.mPayService = payService;
    }

    @Override
    public void pay(String aid, PayChannel channel, int count,int payType) {
        mView.showProgress();
        Call<HttpResponse> call = mPayService.GetCharge(aid, channel.getChannelName(), count, payType);
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                mView.hideProgress();
                if (response.isSuccess()) {
                    HttpResponse httpResponse = response.body();
                    if (httpResponse.ret == 0) {
                        Log.i(TAG, "onResponse: " + httpResponse.data);
                        Gson gson = new Gson();
                        mView.onGetChargeSuccess(gson.toJson(httpResponse.data));
                    } else {
                        mView.onError(httpResponse.msg);
                    }
                } else {
                    mView.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                mView.hideProgress();
                mView.onError(t != null? t.getMessage():"");
            }
        });
    }

    @Override
    public void join(String aid) {
        Call<HttpResponse> call = mActivityService.Join(aid);
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                if (response.isSuccess()) {
                    HttpResponse httpResponse = response.body();
                    Log.i(TAG, "onResponse: " + httpResponse);
                }else{
                    Log.i(TAG, "onResponse: " +response.code()+" "+response.message());
                }
            }
            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                Log.e(TAG, "onResponse: ",t);
            }
        });
    }

    @Override
    public void leave(String aid) {
        Call<HttpResponse> call = mActivityService.Leave(aid);
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                if (response.isSuccess()) {
                    HttpResponse httpResponse = response.body();
                    Log.i(TAG, "onResponse: " + httpResponse);
                }else{
                    Log.i(TAG, "onResponse: " +response.code()+" "+response.message());
                }
            }
            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                Log.e(TAG, "onResponse: ",t);
            }
        });
    }

    @Override
    public void appointment(String aid) {
        Call<HttpResponse> call = mActivityService.appointment(aid);
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                if (response.isSuccess()) {
                    HttpResponse httpResponse = response.body();
                    Log.i(TAG, "onResponse: " + httpResponse);
                    mView.onAppointmentSuccess("预约成功!");
                }else{
                    Log.i(TAG, "onResponse: " +response.code()+" "+response.message());
                    mView.onError("预约失败!");
                }
            }
            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                Log.e(TAG, "onResponse: ",t);
                mView.onError("预约失败!");
            }
        });
    }

    @Override
    public void getOnlineMemberList(String aid) {
        Call<HttpResponse> call = mActivityService.GetOnlineUserList(aid);
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                mView.hideProgress();
                if (response.isSuccess()) {
                    HttpResponse httpResponse = response.body();
                    if (httpResponse.ret == 0) {
                        Log.i(TAG, "onResponse: " + httpResponse.data);
                        Gson gson = new Gson();
                        OnlinUserInfo[] onlinUserInfos = gson.fromJson(httpResponse.data, OnlinUserInfo[].class);
                        if(onlinUserInfos!=null){
                            mView.onGetMemberListSuccess(Arrays.asList(onlinUserInfos));
                        }else{
                            mView.onGetMemberListSuccess(null);
                        }
                    } else {
                        mView.onError(httpResponse.msg);
                    }
                } else {
                    mView.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                mView.hideProgress();
                mView.onError(t != null? t.getMessage():"");
            }
        });
    }
}




