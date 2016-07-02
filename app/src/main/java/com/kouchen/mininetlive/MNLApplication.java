package com.kouchen.mininetlive;

import android.app.Application;
import android.util.Log;

import com.iainconnor.objectcache.CacheManager;
import com.iainconnor.objectcache.DiskCache;
import com.kouchen.mininetlive.rest.RestClient;

import java.io.File;
import java.io.IOException;

import cn.sharesdk.framework.ShareSDK;
import cn.smssdk.SMSSDK;

/**
 * Created by cainli on 16/6/4.
 */
public class MNLApplication extends Application {

    private static final String TAG = "MNLApplication";

    private static RestClient restClient;
    private static CacheManager cacheManager;

    @Override
    public void onCreate() {
        super.onCreate();
        restClient = new RestClient();
        ShareSDK.initSDK(this);
        SMSSDK.initSDK(this, "13ad46f97ff34", "14c2f4b8f54c030c12b6ed47cb79f10e");
        String cachePath = getCacheDir().getPath();
        File cacheFile = new File(cachePath + File.separator + BuildConfig.APPLICATION_ID);

        try {
            DiskCache diskCache = new DiskCache(cacheFile, BuildConfig.VERSION_CODE, 1024 * 1024 * 10);
            cacheManager = CacheManager.getInstance(diskCache);
        } catch (IOException e) {
            Log.e(TAG, "onCreate: ", e);
        }
    }

    public static RestClient getRestClient() {
        return restClient;
    }

    public static CacheManager getCacheManager() {
        return cacheManager;
    }
}
