package com.kouchen.mininetlive;

import android.app.Application;

import cn.smssdk.SMSSDK;
import com.kouchen.mininetlive.rest.RestClient;
import com.pingplusplus.android.Pingpp;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by cainli on 16/6/4.
 */
public class MNLApplication extends Application {
    private static RestClient restClient;
    @Override
    public void onCreate() {
        super.onCreate();
        restClient = new RestClient();
        ShareSDK.initSDK(this);
        SMSSDK.initSDK(this, "13ad46f97ff34", "14c2f4b8f54c030c12b6ed47cb79f10e");
    }

    public static RestClient getRestClient() {
        return restClient;
    }

}
