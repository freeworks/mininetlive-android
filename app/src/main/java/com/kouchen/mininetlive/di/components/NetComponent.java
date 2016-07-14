package com.kouchen.mininetlive.di.components;

import android.content.SharedPreferences;
import com.kouchen.mininetlive.di.modules.AppModule;
import com.kouchen.mininetlive.di.modules.NetModule;
import dagger.Component;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by cainli on 16/7/14.
 */
@Singleton
@Component(modules={AppModule.class, NetModule.class})
public interface NetComponent {

    Retrofit retrofit();
    OkHttpClient okHttpClient();
    SharedPreferences sharedPreferences();
}