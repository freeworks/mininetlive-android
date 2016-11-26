package com.kouchen.mininetlive;

import android.app.ActivityManager;
import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.util.Log;

import com.iainconnor.objectcache.CacheManager;
import com.iainconnor.objectcache.DiskCache;
import com.kouchen.mininetlive.di.components.DaggerNetComponent;
import com.kouchen.mininetlive.di.components.NetComponent;
import com.kouchen.mininetlive.di.modules.AppModule;
import com.kouchen.mininetlive.di.modules.NetModule;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.message.PushAgent;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by cainli on 16/6/4.
 */
public class MNLApplication extends Application {

    private static final String TAG = "MNLApplication";

    protected static MNLApplication mInstance;
    private static CacheManager cacheManager;

    public MNLApplication() {
        mInstance = this;
    }

    private NetComponent mNetComponent;

    private PushAgent mPushAgent;

    @Override
    public void onCreate() {
        super.onCreate();
        long start = SystemClock.uptimeMillis();
        Log.e(TAG,"oncreate....."+System.currentTimeMillis());
        mInstance = this;
        ButterKnife.setDebug(BuildConfig.DEBUG);
        CrashReport.initCrashReport(getApplicationContext(), "900045192", !BuildConfig.DEBUG);

        long cacheStart = SystemClock.uptimeMillis();
        String cachePath = getCacheDir().getPath();
        File cacheFile = new File(cachePath + File.separator + BuildConfig.APPLICATION_ID);
        try {
            DiskCache diskCache = new DiskCache(cacheFile, BuildConfig.VERSION_CODE, 1024 * 1024 * 10);
            cacheManager = CacheManager.getInstance(diskCache);
        } catch (IOException e) {
            Log.e(TAG, "onCreate: ", e);
        }
        Log.e(TAG,"cache init....."+(SystemClock.uptimeMillis() - cacheStart));


        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        if (processAppName == null || !processAppName.equalsIgnoreCase(getPackageName())) {
            Log.e(TAG, "enter the service process!");
            return;
        }
        long daggerStart = SystemClock.uptimeMillis();
        String API_URL = "http://www.weiwanglive.com";
//        String API_URL = "http://192.168.0.102:8080";
        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(API_URL))
                .build();
        Log.e(TAG,"DaggerNetComponent init....."+(SystemClock.uptimeMillis() - daggerStart));

        Log.e(TAG,"oncreate....."+(SystemClock.uptimeMillis() - start));
    }

    public PushAgent getPushAgent() {
        return mPushAgent;
    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return null;
    }

    public static CacheManager getCacheManager() {
        return cacheManager;
    }

    public static boolean sRunningOnIceCreamSandwich;

    static {
        sRunningOnIceCreamSandwich = Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    public static MNLApplication getApplication() {
        if (mInstance != null) {
            return mInstance;
        } else {
            mInstance = new MNLApplication();
            mInstance.onCreate();
            return mInstance;
        }
    }
}
