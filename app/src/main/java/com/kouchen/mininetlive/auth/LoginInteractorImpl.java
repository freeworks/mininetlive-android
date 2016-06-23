package com.kouchen.mininetlive.auth;

import android.util.Log;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;

import com.kouchen.mininetlive.MNLApplication;
import com.kouchen.mininetlive.rest.service.AuthService;
import com.kouchen.mininetlive.rest.service.HttpResponse;

import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginInteractorImpl implements LoginInteractor {

    public static final String TAG = LoginInteractorImpl.class.getSimpleName();

    @Override
    public void oauthLogin(final OnLoginFinishedListener listener, Platform platform, String... args) {
        Platform plat = ShareSDK.getPlatform(platform.getName());
        if (plat == null) {
            return;
        }
        if (plat.isAuthValid()) {
            plat.removeAccount(true);
            return;
        }
        //使用SSO授权，通过客户单授权
        plat.SSOSetting(false);
        plat.setPlatformActionListener(new PlatformActionListener() {
            public void onComplete(Platform plat, int action, HashMap<String, Object> res) {
                if (action == Platform.ACTION_USER_INFOR) {
                    AuthService accountService = MNLApplication.getRestClient().getAccountService();
                    Call<HttpResponse> call = accountService.oauthLogin(plat.getName(),
                            plat.getDb().getUserId(),
                            plat.getDb().getToken(),
                            plat.getDb().getExpiresIn());
                    call.enqueue(new Callback<HttpResponse>() {

                        @Override
                        public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                            if (response.isSuccess()) {
                                HttpResponse httpResponse = response.body();
                                if (httpResponse.ret == 0) {
                                    listener.onSuccess();
                                } else {
                                    listener.onToRegister();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<HttpResponse> call, Throwable t) {
                            Log.e(TAG, "onFailure: ", t);
                        }
                    });
                }
            }

            public void onError(Platform plat, int action, Throwable t) {
                if (action == Platform.ACTION_USER_INFOR) {
                    //
                }
                Log.e(TAG, "", t);
            }

            public void onCancel(Platform plat, int action) {
                if (action == Platform.ACTION_USER_INFOR) {

                }
            }
        });
        plat.showUser(null);
    }

    @Override
    public void login(final OnLoginFinishedListener listener, String phone, String password) {
        AuthService accountService = MNLApplication.getRestClient().getAccountService();
        Call<HttpResponse> call = accountService.login(phone, password);
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                if (!response.isSuccess()) {
                    try {
                        Log.i(TAG, "onResponse: " + response.errorBody().string());
                    } catch (IOException e) {
                        Log.e(TAG, "onResponse: ", e);
                    }
                    return;
                }

                HttpResponse resp = response.body();
                if (resp == null) {
                    return;
                }
                if(resp.ret == 0){
                    listener.onSuccess();
                }else{
                    listener.onError(resp.msg);
                }
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }
}
