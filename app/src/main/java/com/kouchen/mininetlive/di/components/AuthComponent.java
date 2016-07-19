package com.kouchen.mininetlive.di.components;

import com.kouchen.mininetlive.ui.InputInviteCodeActivity;
import com.kouchen.mininetlive.ui.LoginActivity;
import com.kouchen.mininetlive.ui.RegisterStep3Activity;
import com.kouchen.mininetlive.ui.RegisterStep2Activity;
import com.kouchen.mininetlive.ui.RegisterStep1Activity;
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


    void inject(InputInviteCodeActivity activity);

    void inject(LoginActivity activity);

    void inject(RegisterStep3Activity activity);

    void inject(RegisterStep2Activity activity);

    void inject(RegisterStep1Activity activity);

    AuthPresenter presenter();
}
