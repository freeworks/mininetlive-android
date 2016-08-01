package com.kouchen.mininetlive.di.components;

import com.kouchen.mininetlive.di.modules.ActivityDetailModule;
import com.kouchen.mininetlive.di.scopes.ActivityScope;
import com.kouchen.mininetlive.di.scopes.UserScope;
import com.kouchen.mininetlive.presenter.ActivityDetailPresenter;
import com.kouchen.mininetlive.ui.ActivityDetailActivity;
import dagger.Component;

/**
 * Created by cainli on 16/7/14.
 */
@UserScope
@ActivityScope
@Component(dependencies = NetComponent.class,  modules = ActivityDetailModule.class)
public interface ActivityDetailComponent {

    void inject(ActivityDetailActivity activity);

    ActivityDetailPresenter presenter();
}
