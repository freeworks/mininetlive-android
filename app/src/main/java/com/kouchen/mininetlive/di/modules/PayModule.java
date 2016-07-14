package com.kouchen.mininetlive.di.modules;

import android.support.annotation.NonNull;
import com.kouchen.mininetlive.contracts.PayContract;
import com.kouchen.mininetlive.presenter.PayPresenter;
import com.kouchen.mininetlive.di.scopes.ActivityScope;
import com.kouchen.mininetlive.api.PayService;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by cainli on 16/7/14.
 */

@Module
public class PayModule {

    private final PayContract.View mView;

    public PayModule(PayContract.View view) {
        mView = view;
    }

    @Provides
    @ActivityScope
    PayContract.View providePayContractView() {
        return mView;
    }

    @Provides
    @ActivityScope
    PayPresenter providePayresenter(@NonNull PayService payService,
        @NonNull PayContract.View view) {
        return new PayPresenter(payService, view);
    }

    @Provides
    public PayService providesPayService(Retrofit retrofit) {
        return retrofit.create(PayService.class);
    }
}