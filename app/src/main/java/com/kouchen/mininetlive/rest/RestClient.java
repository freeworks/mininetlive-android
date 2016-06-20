package com.kouchen.mininetlive.rest;

import com.kouchen.mininetlive.rest.service.AccountService;
import com.kouchen.mininetlive.rest.service.ActivityService;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cainli on 16/6/4.
 */
public class RestClient {
    public static final String API_URL = "http://127.0.0.1:3000";
    private AccountService accountService;
    private ActivityService activityService;

    public RestClient() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(loggingInterceptor);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        accountService = retrofit.create(AccountService.class);
    }

    public AccountService getAccountService() {
        return accountService;
    }

    public ActivityService getActivityService() {
        return activityService;
    }
}