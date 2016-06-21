package com.kouchen.mininetlive.rest;

import com.kouchen.mininetlive.rest.service.AuthService;
import com.kouchen.mininetlive.rest.service.ActivityService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by cainli on 16/6/4.
 */
public class RestClient {
    public static final String API_URL = "http://127.0.0.1:3000";
    private AuthService accountService;
    private ActivityService activityService;

    public RestClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        accountService = retrofit.create(AuthService.class);
    }

    public AuthService getAccountService() {
        return accountService;
    }

    public ActivityService getActivityService() {
        return activityService;
    }
}