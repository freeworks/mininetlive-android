package com.kouchen.mininetlive.rest;

import com.kouchen.mininetlive.rest.service.AuthService;
import com.kouchen.mininetlive.rest.service.ActivityService;
import com.kouchen.mininetlive.rest.service.PayService;

import java.io.IOException;

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
    private AuthService accountService;
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
                Request original = chain.request();
                Request request = original.newBuilder()
//                        .header("User-Agent", "Your-App-Name")
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
        accountService = retrofit.create(AuthService.class);
        activityService = retrofit.create(ActivityService.class);
        payService = retrofit.create(PayService.class);
    }

    public AuthService getAccountService() {
        return accountService;
    }

    public ActivityService getActivityService() {
        return activityService;
    }

    public PayService getPayService() {
        return payService;
    }
}