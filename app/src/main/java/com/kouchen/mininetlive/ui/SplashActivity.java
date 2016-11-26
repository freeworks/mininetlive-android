package com.kouchen.mininetlive.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.kouchen.mininetlive.BuildConfig;
import com.kouchen.mininetlive.MNLApplication;
import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.ui.base.BaseActivity;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import cn.sharesdk.framework.ShareSDK;
import cn.smssdk.SMSSDK;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Observable.timer(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        finish();
                    }
                });

        ShareSDK.initSDK(this);
        SMSSDK.initSDK(this, "13ad46f97ff34", "14c2f4b8f54c030c12b6ed47cb79f10e");
        push();
    }

    public void push() {
        PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.setDebugMode(BuildConfig.DEBUG);
        // 通知声音由服务端控制
        mPushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SERVER);
        final String UPDATE_STATUS_ACTION = "com.umeng.message.example.action.UPDATE_STATUS";
        //注册推送服务 每次调用register都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                Log.i(TAG, "device token: " + deviceToken);
                MNLApplication.getCacheManager().put("deviceId", deviceToken);
                sendBroadcast(new Intent(UPDATE_STATUS_ACTION));
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.i(TAG, "register failed: " + s + " " + s1);
                sendBroadcast(new Intent(UPDATE_STATUS_ACTION));
            }
        });
    }
}
