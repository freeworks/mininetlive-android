package com.kouchen.mininetlive.di.modules;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import com.kouchen.mininetlive.api.AccountService;
import com.kouchen.mininetlive.api.ActivityService;
import com.kouchen.mininetlive.api.AuthService;
import com.kouchen.mininetlive.api.PayService;
import com.kouchen.mininetlive.contracts.ActivityDetailContract;
import com.kouchen.mininetlive.contracts.BindPhoneContract;
import com.kouchen.mininetlive.di.scopes.ActivityScope;
import com.kouchen.mininetlive.presenter.ActivityDetailPresenter;
import com.kouchen.mininetlive.presenter.BindPhonePresenter;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by cainli on 16/8/1.
 */

@Module
public class BindPhoneModule {

    private final BindPhoneContract.View mView;

    public BindPhoneModule(BindPhoneContract.View view) {
        mView = view;
    }

    @Provides
    @ActivityScope
    BindPhoneContract.View providesBindPhoneContractView() {
        return mView;
    }

    @Provides
    @ActivityScope
    BindPhonePresenter providesBindPhonePresenter(@NonNull AccountService authService,
                                                  @NonNull BindPhoneContract.View view,
                                                  @NonNull SharedPreferences sp) {
        return new BindPhonePresenter(authService, view, sp);
    }

    @Provides
    public AccountService providesAccountService(Retrofit retrofit) {
        return retrofit.create(AccountService.class);
    }

}