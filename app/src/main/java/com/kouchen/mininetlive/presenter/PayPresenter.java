package com.kouchen.mininetlive.presenter;

import android.support.annotation.NonNull;
import android.util.Log;
import com.google.gson.Gson;
import com.kouchen.mininetlive.contracts.PayContract;
import com.kouchen.mininetlive.models.HttpResponse;
import com.kouchen.mininetlive.api.PayService;
import com.kouchen.mininetlive.ui.PayChannel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cainli on 16/6/25.
 */
public class PayPresenter implements PayContract.Presenter {

    private static final String TAG = "PayPresenter";

    private PayContract.View mPayView;

    private PayService mPayService;

    public PayPresenter(@NonNull PayService payService,@NonNull PayContract.View payView) {
        this.mPayView = payView;
        this.mPayService = payService;
    }

    @Override
    public void pay(String aid, PayChannel channel, int count,int payType) {
        mPayView.showProgress();
        Call<HttpResponse> call = mPayService.GetCharge(aid, channel.getChannelName(), count, payType);
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                mPayView.hideProgress();
                if (response.isSuccess()) {
                    HttpResponse httpResponse = response.body();
                    if (httpResponse.ret == 0) {
                        Log.i(TAG, "onResponse: " + httpResponse.data);
                        Gson gson = new Gson();
                        mPayView.onSuccess(gson.toJson(httpResponse.data));
                    } else {
                        mPayView.onError(httpResponse.msg);
                    }
                } else {
                    mPayView.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {
                mPayView.hideProgress();
                mPayView.onError(t != null? t.getMessage():"");
            }
        });
    }

    @Override
    public void withdraw(int count) {

    }
}




