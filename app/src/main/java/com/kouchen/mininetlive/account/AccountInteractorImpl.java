package com.kouchen.mininetlive.account;


import android.util.Log;

import com.google.gson.Gson;
import com.kouchen.mininetlive.MNLApplication;
import com.kouchen.mininetlive.rest.service.AccountService;
import com.kouchen.mininetlive.rest.service.HttpResponse;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountInteractorImpl implements AccountInteractor {

    private static final String TAG = "AccountInteractorImpl";

    @Override
    public void getPlayRecordList(final OnAccountFinishedListener listener) {
        final AccountService accountService = MNLApplication.getRestClient().getAccountService();
        Call<HttpResponse> call = accountService.GetPlayRecordList();
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                if (response.isSuccess()) {
                    HttpResponse httpResponse = response.body();
                    if (httpResponse.ret == 0) {
                        Log.i(TAG, "onResponse: " + httpResponse.data);
                        Gson gson = new Gson();
                        PlayRecordInfo[] playRecordInfos = gson.fromJson(httpResponse.data, PlayRecordInfo[].class);
                        listener.onSuccess(Arrays.asList(playRecordInfos));
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
    public void getPayRecordList(final OnAccountFinishedListener listener) {
        final AccountService accountService = MNLApplication.getRestClient().getAccountService();
        Call<HttpResponse> call = accountService.GetPayRecordList();
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                if (response.isSuccess()) {
                    HttpResponse httpResponse = response.body();
                    if (httpResponse.ret == 0) {
                        Log.i(TAG, "onResponse: " + httpResponse.data);
                        Gson gson = new Gson();
                        PayRecordInfo[] payRecordInfos = gson.fromJson(httpResponse.data, PayRecordInfo[].class);
                        listener.onSuccess(Arrays.asList(payRecordInfos));
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
    public void getAppointRecordList(final OnAccountFinishedListener listener) {
        final AccountService accountService = MNLApplication.getRestClient().getAccountService();
        Call<HttpResponse> call = accountService.GetAppointmentRecordList();
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                if (response.isSuccess()) {
                    HttpResponse httpResponse = response.body();
                    if (httpResponse.ret == 0) {
                        Log.i(TAG, "onResponse: " + httpResponse.data);
                        Gson gson = new Gson();
                        AppointmentRecordInfo[] appointmentRecordInfos = gson.fromJson(httpResponse.data, AppointmentRecordInfo[].class);
                        listener.onSuccess(Arrays.asList(appointmentRecordInfos));
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
    public void getAccountInfo(final OnAccountFinishedListener listener) {

    }
}
