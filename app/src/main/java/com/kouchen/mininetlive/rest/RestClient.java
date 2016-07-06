package com.kouchen.mininetlive.rest;

import com.google.gson.reflect.TypeToken;
import com.kouchen.mininetlive.MNLApplication;
import com.kouchen.mininetlive.auth.UserInfo;
import com.kouchen.mininetlive.rest.service.AccountService;
import com.kouchen.mininetlive.rest.service.AuthService;
import com.kouchen.mininetlive.rest.service.ActivityService;
import com.kouchen.mininetlive.rest.service.PayService;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cainli on 16/6/4.
 */
public class RestClient {
    //    public static final String API_URL = "http://106.75.19.205:8080";
    public static final String API_URL = "http://192.168.0.103:8080";
    private AuthService authService;
    private AccountService accountService;
    private ActivityService activityService;
    private PayService payService;

    public RestClient() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(loggingInterceptor);
        client.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Type userType = new TypeToken<UserInfo>() {
                }.getType();
                UserInfo userInfo = (UserInfo) MNLApplication.getCacheManager().get("user", UserInfo.class, userType);
                Request original = chain.request();
                Request.Builder builder = original.newBuilder();
                if (userInfo == null) {
                    builder.header("uid", userInfo.getUid());
                }
                Request request = builder
                        .header("uid", userInfo.getUid())
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .header("Accept", "application/vnd.yourapi.v1.full+json")
                        .method(original.method(), original.body())
                        .build();
                return chain.proceed(request);
            }
        });


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        authService = retrofit.create(AuthService.class);
        accountService = retrofit.create(AccountService.class);
        activityService = retrofit.create(ActivityService.class);
        payService = retrofit.create(PayService.class);
    }

    public AuthService getAuthService() {
        return authService;
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public ActivityService getActivityService() {
        return activityService;
    }

    public PayService getPayService() {
        return payService;
    }
}