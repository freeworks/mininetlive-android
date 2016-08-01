package com.kouchen.mininetlive.di.modules;

import android.support.annotation.NonNull;
import com.kouchen.mininetlive.api.ActivityService;
import com.kouchen.mininetlive.api.PayService;
import com.kouchen.mininetlive.contracts.ActivityDetailContract;
import com.kouchen.mininetlive.di.scopes.ActivityScope;
import com.kouchen.mininetlive.presenter.ActivityDetailPresenter;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by cainli on 16/8/1.
 */

@Module
public class ActivityDetailModule {

    private final ActivityDetailContract.View mView;

    public ActivityDetailModule(ActivityDetailContract.View view) {
        mView = view;
    }

    @Provides
    @ActivityScope
    ActivityDetailContract.View providesActivityDetailContractView() {
        return mView;
    }

    @Provides
    @ActivityScope
    ActivityDetailPresenter providesActivityDetailPresenter(@NonNull PayService payService,@NonNull ActivityService activityService,
        @NonNull ActivityDetailContract.View view) {
        return new ActivityDetailPresenter(payService, activityService,view);
    }

    @Provides
    public PayService providesPayService(Retrofit retrofit) {
        return retrofit.create(PayService.class);
    }

    @Provides
    public ActivityService providesAcivityService(Retrofit retrofit) {
        return retrofit.create(ActivityService.class);
    }
}