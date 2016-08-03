package com.kouchen.mininetlive.presenter;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.reflect.TypeToken;
import com.kouchen.mininetlive.MNLApplication;
import com.kouchen.mininetlive.api.AuthService;
import com.kouchen.mininetlive.contracts.BindPhoneContract;
import com.kouchen.mininetlive.models.HttpResponse;
import com.kouchen.mininetlive.models.UserInfo;

import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cainli on 16/6/25.
 */
public class BindPhonePresenter implements BindPhoneContract.Presenter {

    private static final String TAG = "AuthPresenter";

    private BindPhoneContract.View mView;

    private AuthService mAuthService;

    private SharedPreferences mSp;

    public BindPhonePresenter(@NonNull AuthService authService,
                              @NonNull BindPhoneContract.View authView,
                              @NonNull SharedPreferences sp) {
        this.mView = authView;
        this.mAuthService = authService;
        this.mSp = sp;
    }

    @Override
    public void getVCode(String phone, final boolean noprogress) {
        if (!noprogress) {
            mView.showProgress();
        }
        Call<HttpResponse> call = mAuthService.getVCode(phone);
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                if (!noprogress) {
                    mView.hideProgress();
                }
                if (response.isSuccess()) {
                    HttpResponse resp = response.body();
                    if (resp.ret == 0) {
//                        mView.onSuccess("获取验证码成功");
                    } else {
                        mView.onError(resp.msg);
                    }
                } else {
                    mView.onError("获取验证码失败");
                }
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                if (!noprogress) {
                    mView.hideProgress();
                }
                mView.onError("获取验证码失败");
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    @Override
    public void resetOrBindPhone(final String phone, String vcode) {
        mView.showProgress();
        Call<HttpResponse> call = mAuthService.bindPhone(phone,vcode);
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                mView.hideProgress();
                if (response.isSuccess()) {
                    HttpResponse httpResponse = response.body();
                    if (httpResponse.ret == 0) {
                        Type userType = new TypeToken<UserInfo>() {
                        }.getType();
                        UserInfo userInfo = (UserInfo) MNLApplication.getCacheManager().get("user", UserInfo.class, userType);
                        userInfo.setPhone(phone);
                        MNLApplication.getCacheManager().put("user",userInfo);
                        mView.onBindSuccess();
                    } else {
                        mView.onError(httpResponse.msg);
                    }
                } else {
                    mView.onError("重置密码失败");
                }
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                mView.hideProgress();
                mView.onError("重置密码失败");
            }
        });
    }
}




