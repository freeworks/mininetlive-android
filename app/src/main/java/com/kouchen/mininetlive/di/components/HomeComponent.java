package com.kouchen.mininetlive.di.components;

import com.kouchen.mininetlive.di.modules.HomeModule;
import com.kouchen.mininetlive.di.scopes.ActivityScope;
import com.kouchen.mininetlive.di.scopes.UserScope;
import com.kouchen.mininetlive.presenter.HomePresenter;
import com.kouchen.mininetlive.ui.HomeFragment;

import dagger.Component;

/**
 * Created by cainli on 16/7/14.
 */
@UserScope
@ActivityScope
@Component(dependencies = NetComponent.class, modules = HomeModule.class)
public interface HomeComponent {

    void inject(HomeFragment fragment);

    HomePresenter presenter();
}
