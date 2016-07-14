package com.kouchen.mininetlive.di.modules;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kouchen.mininetlive.MNLApplication;
import com.kouchen.mininetlive.models.UserInfo;
import dagger.Module;
import dagger.Provides;
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

    String mBaseUrl;

    public NetModule(String baseUrl) {
        this.mBaseUrl = baseUrl;
    }

    @Provides
    @Singleton
    SharedPreferences providesSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

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
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.connectTimeout(20, TimeUnit.SECONDS);
        client.readTimeout(20,TimeUnit.SECONDS);
        client.writeTimeout(20,TimeUnit.SECONDS);
        client.cache(cache).build();
        client.addInterceptor(loggingInterceptor);
        client.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Type userType = new TypeToken<UserInfo>() {
                }.getType();
                UserInfo userInfo = (UserInfo) MNLApplication.getCacheManager()
                    .get("user", UserInfo.class, userType);
                Request original = chain.request();
                Request.Builder builder = original.newBuilder();
                if (userInfo != null) {
                    builder.header("uid", userInfo.getUid());
                }
                Request request =
                    builder.header("Content-Type", "application/x-www-form-urlencoded")
                        .header("Accept", "application/vnd.yourapi.v1.full+json")
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