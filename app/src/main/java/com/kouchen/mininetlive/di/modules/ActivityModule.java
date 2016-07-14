package com.kouchen.mininetlive.di.modules;

import android.support.annotation.NonNull;
import com.kouchen.mininetlive.contracts.ActivityContract;
import com.kouchen.mininetlive.presenter.ActivityPresenter;
import com.kouchen.mininetlive.di.scopes.ActivityScope;
import com.kouchen.mininetlive.api.ActivityService;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by cainli on 16/7/14.
 */

@Module
public class ActivityModule {

    private final ActivityContract.View mView;

    public ActivityModule(ActivityContract.View view) {
        mView = view;
    }

    @Provides
    @ActivityScope
    ActivityContract.View providesActivityContractView() {
        return mView;
    }

    @Provides
    @ActivityScope
    ActivityPresenter providesAuthPresenter(@NonNull ActivityService activityService,
        @NonNull ActivityContract.View view) {
        return new ActivityPresenter(activityService, view);
    }

    @Provides
    public ActivityService providesActivityService(Retrofit retrofit) {
        return retrofit.create(ActivityService.class);
    }
}