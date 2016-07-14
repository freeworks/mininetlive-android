package com.kouchen.mininetlive.di.components;

import com.kouchen.mininetlive.ui.LoginActivity;
import com.kouchen.mininetlive.ui.RegisterInfoActivity;
import com.kouchen.mininetlive.ui.RegisterPasswordActivity;
import com.kouchen.mininetlive.ui.RegisterPhoneActivity;
import com.kouchen.mininetlive.di.modules.AuthModule;
import com.kouchen.mininetlive.presenter.AuthPresenter;
import com.kouchen.mininetlive.di.scopes.ActivityScope;
import com.kouchen.mininetlive.di.scopes.UserScope;
import dagger.Component;

/**
 * Created by cainli on 16/7/14.
 */
@UserScope
@ActivityScope
@Component(dependencies = NetComponent.class, modules = AuthModule.class)
public interface AuthComponent {

    void inject(LoginActivity activity);

    void inject(RegisterInfoActivity activity);

    void inject(RegisterPasswordActivity activity);

    void inject(RegisterPhoneActivity activity);

    AuthPresenter presenter();
}
