package com.kouchen.mininetlive.di.modules;

import android.app.Application;
import com.kouchen.mininetlive.di.scopes.UserScope;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

/**
 * Created by cainli on 16/7/14.
 */
@Module
@UserScope
public class AppModule {

    Application mApplication;

    public AppModule(Application application) {
        mApplication = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return mApplication;
    }
}