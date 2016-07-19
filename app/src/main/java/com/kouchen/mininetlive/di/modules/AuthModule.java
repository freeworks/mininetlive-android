package com.kouchen.mininetlive.di.modules;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.iainconnor.objectcache.CacheManager;
import com.kouchen.mininetlive.contracts.AuthContract;
import com.kouchen.mininetlive.presenter.AuthPresenter;
import com.kouchen.mininetlive.di.scopes.ActivityScope;
import com.kouchen.mininetlive.api.AuthService;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by cainli on 16/7/14.
 */

@Module
public class AuthModule {

    private final AuthContract.View mView;

    public AuthModule(AuthContract.View view) {
        mView = view;
    }

    @Provides
    @ActivityScope
    AuthContract.View providesAuthContractView() {
        return mView;
    }

    @Provides
    @ActivityScope
    AuthPresenter providesAuthPresenter(@NonNull AuthService authService,
                                        @NonNull AuthContract.View view,
                                        @NonNull SharedPreferences sp) {
        return new AuthPresenter(authService, view, sp);
    }

    @Provides
    public AuthService providesAuthService(Retrofit retrofit) {
        return retrofit.create(AuthService.class);
    }
}