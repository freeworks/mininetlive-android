package com.kouchen.mininetlive;

import android.app.ActivityManager;
import android.app.Application;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.util.DisplayMetrics;
import android.util.Log;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.iainconnor.objectcache.CacheManager;
import com.iainconnor.objectcache.DiskCache;
import com.kouchen.mininetlive.rest.RestClient;
import com.umeng.message.PushAgent;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import butterknife.ButterKnife;
import cn.sharesdk.framework.ShareSDK;
import cn.smssdk.SMSSDK;

/**
 * Created by cainli on 16/6/4.
 */
public class MNLApplication extends Application {

    private static final String TAG = "MNLApplication";

    protected static MNLApplication mInstance;
    private DisplayMetrics displayMetrics = null;
    private static RestClient restClient;
    private static CacheManager cacheManager;

    public MNLApplication() {
        mInstance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        restClient = new RestClient();
        ButterKnife.setDebug(true);
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

        ShareSDK.initSDK(this);
        SMSSDK.initSDK(this, "13ad46f97ff34", "14c2f4b8f54c030c12b6ed47cb79f10e");
        EMOptions options = new EMOptions();
        options.allowChatroomOwnerLeave(true);
        options.setAcceptInvitationAlways(true);
        options.setAutoAcceptGroupInvitation(true);
        options.setAcceptInvitationAlways(false);
        EMClient.getInstance().init(this, options);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);

        final PushAgent mPushAgent = PushAgent.getInstance(this);
        mPushAgent.enable();
        new AsyncTask<Void,Void,Void>(){

            @Override
            protected Void doInBackground(Void... voids) {
                try {
                    mPushAgent.getTagManager().add("test1");
                } catch (Exception e) {
                    Log.e(TAG, "onCreate: ",e);
                }
                return null;
            }
        }.execute();
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

    public static RestClient getRestClient() {
        return restClient;
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
