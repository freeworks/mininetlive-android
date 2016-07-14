package com.kouchen.mininetlive.di.modules;

import android.support.annotation.NonNull;
import com.kouchen.mininetlive.contracts.AccountContract;
import com.kouchen.mininetlive.presenter.AccountPresenter;
import com.kouchen.mininetlive.di.scopes.ActivityScope;
import com.kouchen.mininetlive.api.AccountService;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by cainli on 16/7/14.
 */

@Module
public class AccountModule {

    private final AccountContract.View mView;

    public AccountModule(AccountContract.View view) {
        mView = view;
    }

    @Provides
    @ActivityScope
    AccountContract.View providesAccountContractView() {
        return mView;
    }

    @Provides
    @ActivityScope
    AccountPresenter providesAccountPresenter(@NonNull AccountService  accountService,
        @NonNull AccountContract.View view) {
        return new AccountPresenter(accountService, view);
    }

    @Provides
    public AccountService providesAccountService(Retrofit retrofit) {
        return retrofit.create(AccountService.class);
    }
}