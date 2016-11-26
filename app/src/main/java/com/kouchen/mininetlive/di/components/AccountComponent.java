package com.kouchen.mininetlive.di.components;

import com.kouchen.mininetlive.di.modules.AccountModule;
import com.kouchen.mininetlive.presenter.AccountPresenter;
import com.kouchen.mininetlive.di.scopes.ActivityScope;
import com.kouchen.mininetlive.di.scopes.UserScope;
import com.kouchen.mininetlive.ui.AppointmentRecordActivity;
import com.kouchen.mininetlive.ui.DividendListActivity;
import com.kouchen.mininetlive.ui.WithdrawActivity;
import com.kouchen.mininetlive.ui.EditGenderActivity;
import com.kouchen.mininetlive.ui.EditMeActivity;
import com.kouchen.mininetlive.ui.EditNickActivity;
import com.kouchen.mininetlive.ui.InviteCodeActivity;
import com.kouchen.mininetlive.ui.PayRecordActivity;
import com.kouchen.mininetlive.ui.PlayRecordActivity;
import com.kouchen.mininetlive.ui.WithdrawRecordActivity;
import dagger.Component;

/**
 * Created by cainli on 16/7/14.
 */
@UserScope
@ActivityScope
@Component(dependencies = NetComponent.class, modules = AccountModule.class)
public interface AccountComponent {

    void inject(AppointmentRecordActivity activity);

    void inject(EditGenderActivity activity);

    void inject(EditMeActivity activity);

    void inject(EditNickActivity activity);

    void inject(InviteCodeActivity activity);

    void inject(PlayRecordActivity activity);

    void inject(PayRecordActivity activity);

    void inject(WithdrawRecordActivity activity);

    void inject(DividendListActivity activity);

    AccountPresenter presenter();
}
