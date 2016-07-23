package com.kouchen.mininetlive.presenter;

import android.support.annotation.NonNull;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kouchen.mininetlive.MNLApplication;
import com.kouchen.mininetlive.contracts.AccountContract;
import com.kouchen.mininetlive.models.AppointmentRecordInfo;
import com.kouchen.mininetlive.models.PayRecordInfo;
import com.kouchen.mininetlive.models.PlayRecordInfo;
import com.kouchen.mininetlive.models.UserInfo;
import com.kouchen.mininetlive.models.WithdrawRecordInfo;
import com.kouchen.mininetlive.api.AccountService;
import com.kouchen.mininetlive.models.HttpResponse;

import java.io.File;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cainli on 16/7/14.
 */
public class AccountPresenter implements AccountContract.Presenter {

    private static final String TAG = "ActivityPresenter";

    private AccountContract.View mAccountView;

    private AccountService mAccountService;

    public AccountPresenter(@NonNull AccountService accountService,
        @NonNull AccountContract.View accountView) {
        this.mAccountView = accountView;
        this.mAccountService = accountService;
    }

    @Override
    public void getPlayRecordList() {
        mAccountView.showProgress();
        Call<HttpResponse> call = mAccountService.GetPlayRecordList();
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                mAccountView.hideProgress();
                if (response.isSuccess()) {
                    HttpResponse httpResponse = response.body();
                    if (httpResponse.ret == 0) {
                        Log.i(TAG, "onResponse: " + httpResponse.data);
                        Gson gson = new Gson();
                        PlayRecordInfo[] playRecordInfos = gson.fromJson(httpResponse.data, PlayRecordInfo[].class);
                        mAccountView.onSuccess(Arrays.asList(playRecordInfos));
                    } else {
                        mAccountView.onError(httpResponse.msg);
                    }
                } else {
                    mAccountView.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                mAccountView.hideProgress();
                mAccountView.onError("获取失败");
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    @Override
    public void getPayRecordList() {
        mAccountView.showProgress();
        Call<HttpResponse> call = mAccountService.GetPayRecordList();
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                mAccountView.hideProgress();
                if (response.isSuccess()) {
                    HttpResponse httpResponse = response.body();
                    if (httpResponse.ret == 0) {
                        Log.i(TAG, "onResponse: " + httpResponse.data);
                        Gson gson = new Gson();
                        PayRecordInfo[] payRecordInfos = gson.fromJson(httpResponse.data, PayRecordInfo[].class);
                        mAccountView.onSuccess(Arrays.asList(payRecordInfos));
                    } else {
                        mAccountView.onError(httpResponse.msg);
                    }
                } else {
                    mAccountView.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                mAccountView.hideProgress();
                mAccountView.onError("获取失败");
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    @Override
    public void getAppointRecordList() {
        mAccountView.showProgress();
        Call<HttpResponse> call = mAccountService.GetAppointmentRecordList();
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                mAccountView.hideProgress();
                if (response.isSuccess()) {
                    HttpResponse httpResponse = response.body();
                    if (httpResponse.ret == 0) {
                        Log.i(TAG, "onResponse: " + httpResponse.data);
                        Gson gson = new Gson();
                        AppointmentRecordInfo[] appointmentRecordInfos = gson.fromJson(httpResponse.data, AppointmentRecordInfo[].class);
                        mAccountView.onSuccess(Arrays.asList(appointmentRecordInfos));
                    } else {
                        mAccountView.onError(httpResponse.msg);
                    }
                } else {
                    mAccountView.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                mAccountView.hideProgress();
                mAccountView.onError("获取失败");
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    @Override
    public void getWithdrawRecordList() {
        mAccountView.showProgress();
        Call<HttpResponse> call = mAccountService.GetWithdrawRecordList();
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                mAccountView.hideProgress();
                if (response.isSuccess()) {
                    HttpResponse httpResponse = response.body();
                    if (httpResponse.ret == 0) {
                        Log.i(TAG, "onResponse: " + httpResponse.data);
                        Gson gson = new Gson();
                        WithdrawRecordInfo[] WithdrawRecordInfos = gson.fromJson(httpResponse.data, WithdrawRecordInfo[].class);
                        mAccountView.onSuccess(Arrays.asList(WithdrawRecordInfos));
                    } else {
                        mAccountView.onError(httpResponse.msg);
                    }
                } else {
                    mAccountView.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                mAccountView.hideProgress();
                mAccountView.onError("获取失败");
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    @Override
    public void getAccountInfo() {

    }

    @Override
    public void uploadAvatar(String filepath) {
        mAccountView.showProgress();

        File file = new File(filepath);
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        String fileName = "file\"; filename=\"" + file.getName();
        requestBodyMap.put(fileName, RequestBody.create(MediaType.parse("multipart/form-data"), file));
        Call<HttpResponse> call = mAccountService.uploadAvatar(requestBodyMap);
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                mAccountView.hideProgress();
                if (response.isSuccess()) {
                    HttpResponse httpResponse = response.body();
                    if (httpResponse.ret == 0) {
                        Log.i(TAG, "onResponse: " + httpResponse.data);
                        String url = httpResponse.data.getAsJsonObject().get("url").getAsString();
                        Type userType = new TypeToken<UserInfo>() {
                        }.getType();
                        UserInfo userInfo = (UserInfo) MNLApplication.getCacheManager().get("user", UserInfo.class, userType);
                        userInfo.setAvatar(url);
                        MNLApplication.getCacheManager().put("user",userInfo);
                        mAccountView.onSuccess(url);
                    } else {
                        mAccountView.onError(httpResponse.msg);
                    }
                } else {
                    mAccountView.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                mAccountView.hideProgress();
                mAccountView.onError("更改头像失败");
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    @Override
    public void updateNickname(String name) {
        mAccountView.showProgress();
        Call<HttpResponse> call = mAccountService.updateNickname(name);
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                mAccountView.hideProgress();
                if (response.isSuccess()) {
                    HttpResponse httpResponse = response.body();
                    if (httpResponse.ret == 0) {
                        Log.i(TAG, "onResponse: " + httpResponse.data);
                        mAccountView.onSuccess(null);
                    } else {
                        mAccountView.onError(httpResponse.msg);
                    }
                } else {
                    mAccountView.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                mAccountView.hideProgress();
                mAccountView.onError("更改昵称失败");
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }
}
