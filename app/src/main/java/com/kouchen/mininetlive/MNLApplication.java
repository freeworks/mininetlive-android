package com.kouchen.mininetlive;

import android.app.ActivityManager;
import android.app.Application;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import butterknife.ButterKnife;
import cn.sharesdk.framework.ShareSDK;
import cn.smssdk.SMSSDK;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.iainconnor.objectcache.CacheManager;
import com.iainconnor.objectcache.DiskCache;
import com.kouchen.mininetlive.api.CommonService;
import com.kouchen.mininetlive.di.components.DaggerNetComponent;
import com.kouchen.mininetlive.di.components.NetComponent;
import com.kouchen.mininetlive.di.modules.AppModule;
import com.kouchen.mininetlive.di.modules.NetModule;
import com.kouchen.mininetlive.models.HomeModel;
import com.kouchen.mininetlive.models.HttpResponse;
import com.kouchen.mininetlive.ui.ActivityDetailActivity;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.message.ALIAS_TYPE;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.MsgConstant;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.common.UmLog;
import com.umeng.message.entity.UMessage;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * Created by cainli on 16/6/4.
 */
public class MNLApplication extends Application {

    private static final String TAG = "MNLApplication";

    protected static MNLApplication mInstance;
    private DisplayMetrics displayMetrics = null;
    private static CacheManager cacheManager;

    public MNLApplication() {
        mInstance = this;
    }

    private NetComponent mNetComponent;

    private PushAgent mPushAgent;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        ButterKnife.setDebug(true);
        CrashReport.initCrashReport(getApplicationContext(), "900045192", false);

        String cachePath = getCacheDir().getPath();
        File cacheFile = new File(cachePath + File.separator + BuildConfig.APPLICATION_ID);
        try {
            DiskCache diskCache = new DiskCache(cacheFile, BuildConfig.VERSION_CODE, 1024 * 1024 * 10);
            cacheManager = CacheManager.getInstance(diskCache);
        } catch (IOException e) {
            Log.e(TAG, "onCreate: ", e);
        }


        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        if (processAppName == null || !processAppName.equalsIgnoreCase(getPackageName())) {
            Log.e(TAG, "enter the service process!");
            return;
        }
//        String API_URL = "http://www.weiwanglive.com";
//        String API_URL = "http://106.75.19.205:80";
        String API_URL = "http://192.168.0.102:8080";
//        String API_URL = "http://172.17.23.194:8080";
        mNetComponent = DaggerNetComponent.builder()
                .appModule(new AppModule(this))
                .netModule(new NetModule(API_URL))
                .build();
    }

    public void setPushAgent(PushAgent mPushAgent) {
        this.mPushAgent = mPushAgent;
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
        return processName;
    }

    public static CacheManager getCacheManager() {
        return cacheManager;
    }

    public static boolean sRunningOnIceCreamSandwich;

    static {
        sRunningOnIceCreamSandwich = Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
    }

    public static MNLApplication getApplication() {
        if (mInstance != null && mInstance instanceof MNLApplication) {
            return (MNLApplication) mInstance;
        } else {
            mInstance = new MNLApplication();
            mInstance.onCreate();
            return (MNLApplication) mInstance;
        }
    }

    public float getScreenDensity() {
        if (this.displayMetrics == null) {
            setDisplayMetrics(getResources().getDisplayMetrics());
        }
        return this.displayMetrics.density;
    }

    public int getScreenHeight() {
        if (this.displayMetrics == null) {
            setDisplayMetrics(getResources().getDisplayMetrics());
        }
        return this.displayMetrics.heightPixels;
    }

    public int getScreenWidth() {
        if (this.displayMetrics == null) {
            setDisplayMetrics(getResources().getDisplayMetrics());
        }
        return this.displayMetrics.widthPixels;
    }

    public void setDisplayMetrics(DisplayMetrics DisplayMetrics) {
        this.displayMetrics = DisplayMetrics;
    }

    public int dp2px(float f) {
        return (int) (0.5F + f * getScreenDensity());
    }

    public int px2dp(float pxValue) {
        return (int) (pxValue / getScreenDensity() + 0.5f);
    }

    //获取应用的data/data/....File目录
    public String getFilesDirPath() {
        return getFilesDir().getAbsolutePath();
    }

    //获取应用的data/data/....Cache目录
    public String getCacheDirPath() {
        return getCacheDir().getAbsolutePath();
    }


}
