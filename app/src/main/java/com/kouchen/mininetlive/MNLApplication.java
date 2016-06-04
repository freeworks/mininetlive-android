package com.kouchen.mininetlive;

import android.app.Application;

import com.kouchen.mininetlive.rest.RestClient;

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
    }

    public static RestClient getRestClient() {
        return restClient;
    }

}
