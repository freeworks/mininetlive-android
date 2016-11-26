package com.kouchen.mininetlive.di.modules;

import android.support.annotation.NonNull;

import com.kouchen.mininetlive.api.PayService;
import com.kouchen.mininetlive.contracts.WithdrawContract;
import com.kouchen.mininetlive.di.scopes.ActivityScope;
import com.kouchen.mininetlive.presenter.WithdrawPresenter;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by cainli on 16/7/14.
 */

@Module
public class WithdrawModule {

    private final WithdrawContract.View mView;

    public WithdrawModule(WithdrawContract.View view) {
        mView = view;
    }

    @Provides
    @ActivityScope
    WithdrawContract.View provideWithdrawContractView() {
        return mView;
    }

    @Provides
    @ActivityScope
    WithdrawPresenter provideWithdrawPresenter(@NonNull PayService payService,
                                         @NonNull WithdrawContract.View view) {
        return new WithdrawPresenter(payService, view);
    }

    @Provides
    public PayService providesPayService(Retrofit retrofit) {
        return retrofit.create(PayService.class);
    }
}