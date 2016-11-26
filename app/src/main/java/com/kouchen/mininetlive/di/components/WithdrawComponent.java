package com.kouchen.mininetlive.di.components;

import com.kouchen.mininetlive.di.modules.WithdrawModule;
import com.kouchen.mininetlive.di.scopes.ActivityScope;
import com.kouchen.mininetlive.di.scopes.UserScope;
import com.kouchen.mininetlive.presenter.WithdrawPresenter;
import com.kouchen.mininetlive.ui.WithdrawActivity;

import dagger.Component;

/**
 * Created by cainli on 16/7/14.
 */
@UserScope
@ActivityScope
@Component(dependencies = NetComponent.class, modules = WithdrawModule.class)
public interface WithdrawComponent {

    void inject(WithdrawActivity activity);

    WithdrawPresenter presenter();
}
