package com.kouchen.mininetlive.di.components;

import com.kouchen.mininetlive.di.modules.LiveModule;
import com.kouchen.mininetlive.di.scopes.ActivityScope;
import com.kouchen.mininetlive.di.scopes.UserScope;
import com.kouchen.mininetlive.presenter.LivePresenter;
import com.kouchen.mininetlive.ui.LiveFragment;

import dagger.Component;

/**
 * Created by cainli on 16/7/14.
 */
@UserScope
@ActivityScope
@Component(dependencies = NetComponent.class, modules = LiveModule.class)
public interface LiveComponent {

    void inject(LiveFragment fragment);

    LivePresenter presenter();
}
