package com.kouchen.mininetlive.di.components;

import com.kouchen.mininetlive.di.modules.ActivityModule;
import com.kouchen.mininetlive.presenter.ActivityPresenter;
import com.kouchen.mininetlive.di.scopes.ActivityScope;
import com.kouchen.mininetlive.di.scopes.UserScope;
import com.kouchen.mininetlive.ui.ActivityDetailActivity;
import com.kouchen.mininetlive.ui.HomeFragment;
import com.kouchen.mininetlive.ui.LiveFragment;
import com.kouchen.mininetlive.ui.MainActivity;

import dagger.Component;

/**
 * Created by cainli on 16/7/14.
 */
@UserScope
@ActivityScope
@Component(dependencies = NetComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity activity);

    void inject(HomeFragment fragment);

    void inject(LiveFragment fragment);

    ActivityPresenter presenter();
}
