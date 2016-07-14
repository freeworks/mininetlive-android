package com.kouchen.mininetlive.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;
import com.kouchen.mininetlive.contracts.PayContract;
import com.kouchen.mininetlive.ui.base.AbsTitlebarActivity;
import com.pingplusplus.android.Pingpp;
import com.pingplusplus.android.PingppLog;

/**
 * Created by cainli on 16/6/21.
 */
public  class PayActivity extends AbsTitlebarActivity {
    private static final String TAG = PayActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PingppLog.DEBUG = true;
    }

    @Override
    protected int getContentResId() {
        return 0;
    }

    @Override
    public String getTitleString() {
        return null;
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
    
}
