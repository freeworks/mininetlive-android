package com.kouchen.mininetlive.pay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kouchen.mininetlive.AbsTitlebarActivity;
import com.kouchen.mininetlive.MNLApplication;
import com.kouchen.mininetlive.activity.ActivityInfo;
import com.kouchen.mininetlive.base.BaseActivity;
import com.kouchen.mininetlive.rest.service.ActivityService;
import com.kouchen.mininetlive.rest.service.HttpResponse;
import com.kouchen.mininetlive.rest.service.PayService;
import com.pingplusplus.android.Pingpp;
import com.pingplusplus.android.PingppLog;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by cainli on 16/6/21.
 */
public abstract class PayActivity extends AbsTitlebarActivity {
    private static final String TAG = PayActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PingppLog.DEBUG = true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //支付页面返回处理
        if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
                if (result == null) {
                    Toast.makeText(this, "支付失败!", Toast.LENGTH_SHORT).show();
                    return;
                }
                String msg = null;
                switch (result) {
                    case "success":
                        msg = "支付成功!";
                        break;
                    case "fail":
                        msg = "支付失败，请重新支付!";
                        break;
                    case "cancel":
                        msg = "取消支付成功!";
                        break;
                    case "invalid":
                        msg = "请检查是安装微信/支付宝客户端!";
                        break;
                }
                if (msg != null) {
                    Toast.makeText(PayActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
                // 处理返回值
                // "success" - 支付成功
                // "fail"    - 支付失败
                // "cancel"  - 取消支付
                // "invalid" - 支付插件未安装（一般是微信客户端未安装的情况）
                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
                String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
                Log.i(TAG, "errorMsg:" + errorMsg);
                Log.i(TAG, "extraMsg:" + extraMsg);
            }
        }
    }

    public void onGetChargeSuccess(String data) {
        Pingpp.createPayment(this, data);
    }

    public void pay(PayChannel channel, int count) {
        final PayService payService = MNLApplication.getRestClient().getPayService();
        Call<HttpResponse> call = payService.GetCharge(channel.getChannelName(),count,1);
        call.enqueue(new Callback<HttpResponse>() {
            @Override
            public void onResponse(Call<HttpResponse> call, Response<HttpResponse> response) {
                if (response.isSuccess()) {
                    HttpResponse httpResponse = response.body();
                    if (httpResponse.ret == 0) {
                        Log.i(TAG, "onResponse: " + httpResponse.data);
                        Log.i(TAG, "onResponse: "+ httpResponse.data);
                        Pingpp.createPayment(PayActivity.this, ((String)httpResponse.data));
                    } else {
//                        listener.onError(httpResponse.msg);
                    }
                } else {
//                    listener.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<HttpResponse> call, Throwable t) {

            }
        });
    }

    private void getCharge() {

    }


}
