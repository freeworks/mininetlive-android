package com.kouchen.mininetlive.di.components;

import com.kouchen.mininetlive.di.modules.BindPhoneModule;
import com.kouchen.mininetlive.di.scopes.ActivityScope;
import com.kouchen.mininetlive.di.scopes.UserScope;
import com.kouchen.mininetlive.presenter.BindPhonePresenter;
import com.kouchen.mininetlive.ui.BindPhoneActivity;

import dagger.Component;

/**
 * Created by cainli on 16/7/14.
 */
@UserScope
@ActivityScope
@Component(dependencies = NetComponent.class, modules = BindPhoneModule.class)
public interface BindPhoneComponent {

    void inject(BindPhoneActivity activity);

    BindPhonePresenter presenter();
}
