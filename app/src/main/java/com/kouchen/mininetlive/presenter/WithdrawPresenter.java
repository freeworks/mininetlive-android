package com.kouchen.mininetlive.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.kouchen.mininetlive.api.PayService;
import com.kouchen.mininetlive.contracts.WithdrawContract;
import com.kouchen.mininetlive.models.HttpResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cainli on 2016/11/27.
 */
public class WithdrawPresenter implements WithdrawContract.Presenter {

    private static final String TAG = "WithdrawPresenter";

    private WithdrawContract.View mPayView;

    private PayService mPayService;

    public WithdrawPresenter(@NonNull PayService payService, @NonNull WithdrawContract.View payView) {
        this.mPayView = payView;
        this.mPayService = payService;
    }

    @Override
    public void withdraw(int count) {
        mPayView.showProgress("提现中...");
        Call<HttpResponse> call = mPayService.Withdraw(count);
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                mPayView.hideProgress();
                if (response.isSuccess()) {
                    HttpResponse httpResponse = response.body();
                    switch (httpResponse.ret) {
                        case 0:
                            mPayView.onSuccess(httpResponse.msg);
                            break;
                        case 1013:
                            mPayView.onError(httpResponse.msg);
                            break;
                        case 2001:
                            mPayView.onError(httpResponse.msg);
                            break;
                        case 2006:
                            mPayView.onUnbindPhone();
                            break;
                        case 2007:
                            mPayView.onUnbindWxPub();
                            break;
                        case 2008:
                            mPayView.onError(httpResponse.msg);
                            break;
                        case 2009:
                            mPayView.onError(httpResponse.msg);
                            break;
                    }
                } else {
                    mPayView.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                mPayView.hideProgress();
                mPayView.onError(t != null ? t.getMessage() : "");
            }
        });
    }

    @Override
    public void refreshBalance() {
        Call<HttpResponse> call = mPayService.getBalance();
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                mPayView.hideProgress();
                if (response.isSuccess()) {
                    HttpResponse httpResponse = response.body();
                    if (httpResponse.ret == 0) {
                        Log.i(TAG, "onResponse: " + httpResponse.data);
                        long balance = httpResponse.data.getAsJsonObject().get("balance").getAsLong();
                        mPayView.onGetBalanceSuccess(balance);
                    }
                }
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                mPayView.hideProgress();
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    @Override
    public void getBalance() {
        mPayView.showProgress("获取余额中...");
        Call<HttpResponse> call = mPayService.getBalance();
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                mPayView.hideProgress();
                if (response.isSuccess()) {
                    HttpResponse httpResponse = response.body();
                    if (httpResponse.ret == 0) {
                        Log.i(TAG, "onResponse: " + httpResponse.data);
                        long balance = httpResponse.data.getAsJsonObject().get("balance").getAsLong();
                        mPayView.onGetBalanceSuccess(balance);
                    } else {
                        mPayView.onGetBalanceError("获取余额失败!");
                    }
                } else {
                    mPayView.onGetBalanceError("获取余额失败!");
                }
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                mPayView.hideProgress();
                mPayView.onGetBalanceError("获取余额失败!");
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }
}
