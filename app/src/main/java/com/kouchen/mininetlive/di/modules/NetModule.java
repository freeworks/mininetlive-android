package com.kouchen.mininetlive.di.modules;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.iainconnor.objectcache.CacheManager;
import com.iainconnor.objectcache.DiskCache;
import com.kouchen.mininetlive.BuildConfig;
import com.kouchen.mininetlive.MNLApplication;
import com.kouchen.mininetlive.models.UserInfo;

import dagger.Module;
import dagger.Provides;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cainli on 16/7/14.
 */
@Module
public class NetModule {

    private static final String TAG = "NetModule";

    String mBaseUrl;

    public NetModule(String baseUrl) {
        this.mBaseUrl = baseUrl;
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

//    @Provides
//    @Singleton
//    CacheManager providesCacheManager(Application application) {
//        String cachePath = application.getCacheDir().getPath();
//        File cacheFile = new File(cachePath + File.separator + BuildConfig.APPLICATION_ID);
//        DiskCache diskCache = null;
//        try {
//            diskCache = new DiskCache(cacheFile, BuildConfig.VERSION_CODE, 1024 * 1024 * 10);
//        } catch (IOException e) {
//            Log.e(TAG, "", e);
//        }
//        return CacheManager.getInstance(diskCache);
//    }

    @Provides
    @Singleton
    Cache provideOkHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }

    @Provides  // Dagger will only look for methods annotated with @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        return gsonBuilder.create();
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Cache cache) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.connectTimeout(30, TimeUnit.SECONDS);
        client.readTimeout(30, TimeUnit.SECONDS);
        client.writeTimeout(30, TimeUnit.SECONDS);
        client.cache(cache).build();
        client.addInterceptor(loggingInterceptor);
        client.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                CacheManager cacheManager = MNLApplication.getCacheManager();
                UserInfo userInfo = (UserInfo) cacheManager.get("user", UserInfo.class, new TypeToken<UserInfo>() {}.getType());
//                String deviceId = (String) cacheManager.get("deviceId", String.class, new TypeToken<String>() {}.getType());
                Request original = chain.request();
                Request.Builder builder = original.newBuilder();
                if (userInfo != null) {
                    builder.header("uid", userInfo.getUid());
                }
//                builder.header("deviceId",deviceId);
                Request request = builder.header("Accept", "application/vnd.yourapi.v1.full+json")
                                .method(original.method(), original.body())
                                .build();
                return chain.proceed(request);
            }
        });
        return client.build();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient) {
        Retrofit retrofit =
                new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
                        .baseUrl(mBaseUrl)
                        .client(okHttpClient)
                        .build();
        return retrofit;
    }
}