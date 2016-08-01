package com.kouchen.mininetlive.presenter;

import android.support.annotation.NonNull;
import android.util.Log;
import com.google.gson.Gson;
import com.kouchen.mininetlive.api.ActivityService;
import com.kouchen.mininetlive.api.PayService;
import com.kouchen.mininetlive.contracts.ActivityDetailContract;
import com.kouchen.mininetlive.models.HttpResponse;
import com.kouchen.mininetlive.ui.PayChannel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
                        mView.onSuccess(gson.toJson(httpResponse.data));
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

    }

    @Override
    public void leave(String aid) {

    }

    @Override
    public void appointment(String aid) {

    }

    @Override
    public void getOnlineMemberList(String aid) {

    }
}




