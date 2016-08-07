package com.kouchen.mininetlive.di.modules;

import android.support.annotation.NonNull;

import com.kouchen.mininetlive.api.ActivityService;
import com.kouchen.mininetlive.contracts.HomeContract;
import com.kouchen.mininetlive.di.scopes.ActivityScope;
import com.kouchen.mininetlive.presenter.HomePresenter;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by cainli on 16/7/14.
 */

@Module
public class HomeModule {

    private final HomeContract.View mView;

    public HomeModule(HomeContract.View view) {
        mView = view;
    }

    @Provides
    @ActivityScope
    HomeContract.View providesHomeContractView() {
        return mView;
    }

    @Provides
    @ActivityScope
    HomePresenter providesHomePresenter(@NonNull ActivityService activityService,
                                        @NonNull HomeContract.View view) {
        return new HomePresenter(activityService, view);
    }

    @Provides
    public ActivityService providesActivityService(Retrofit retrofit) {
        return retrofit.create(ActivityService.class);
    }
}