package com.kouchen.mininetlive.di.components;

import com.kouchen.mininetlive.di.modules.PayModule;
import com.kouchen.mininetlive.presenter.PayPresenter;
import com.kouchen.mininetlive.di.scopes.ActivityScope;
import com.kouchen.mininetlive.di.scopes.UserScope;
import com.kouchen.mininetlive.ui.ActivityDetailActivity;
import dagger.Component;

/**
 * Created by cainli on 16/7/14.
 */
@UserScope
@ActivityScope
@Component(dependencies = NetComponent.class, modules = PayModule.class)
public interface PayComponent {

    void inject(ActivityDetailActivity activity);

    PayPresenter presenter();
}
