package com.kouchen.mininetlive.di.modules;

import android.support.annotation.NonNull;

import com.kouchen.mininetlive.api.ActivityService;
import com.kouchen.mininetlive.contracts.HomeContract;
import com.kouchen.mininetlive.contracts.LiveContract;
import com.kouchen.mininetlive.di.components.LiveComponent;
import com.kouchen.mininetlive.di.scopes.ActivityScope;
import com.kouchen.mininetlive.presenter.HomePresenter;
import com.kouchen.mininetlive.presenter.LivePresenter;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by cainli on 16/7/14.
 */

@Module
public class LiveModule {

    private final LiveContract.View mView;

    public LiveModule(LiveContract.View view) {
        mView = view;
    }

    @Provides
    @ActivityScope
    LiveContract.View providesLiveContractView() {
        return mView;
    }

    @Provides
    @ActivityScope
    LivePresenter providesLivePresenter(@NonNull ActivityService activityService,
                                        @NonNull LiveContract.View view) {
        return new LivePresenter(activityService, view);
    }

    @Provides
    public ActivityService providesActivityService(Retrofit retrofit) {
        return retrofit.create(ActivityService.class);
    }
}