package com.kouchen.mininetlive.auth;

import cn.sharesdk.framework.Platform;

public class AuthPresenterImpl implements AuthPresenter, AuthInteractor.OnLoginFinishedListener {

    private AuthView loginView;
    private AuthInteractor loginInteractor;

    public AuthPresenterImpl(AuthView loginView) {
        this.loginView = loginView;
        this.loginInteractor = new AuthInteractorImpl();
    }

    @Override
    public void validateCredentials(Platform platform, String... args) {
        loginInteractor.oauthLogin(this, platform, args);
    }

    @Override
    public void getVCode(String phone) {
        loginInteractor.getVCode(this, phone);
    }

    @Override
    public void register(String phone, String vcode, String password, String inviteCode) {
        if (loginView != null) {
            loginView.showProgress();
        }
        loginInteractor.register(this, phone, vcode, password, inviteCode);
    }

    @Override
    public void login(String phone, String password) {
        if (loginView != null) {
            loginView.showProgress();
        }
        loginInteractor.login(this, phone, password);
    }

    @Override
    public void onDestroy() {
        loginView = null;
    }


    @Override
    public void onSuccess() {
        if (loginView != null) {
            loginView.navigateToHome();
        }
    }

    @Override
    public void onError(String msg) {
        if (loginView != null) {
            loginView.setError(msg);
            loginView.hideProgress();
        }
    }

    @Override
    public void onRegisting() {
        if (loginView != null) {
            loginView.showProgress();
        }
    }

    @Override
    public void onRegisterSuccess() {
        if (loginView != null) {
            loginView.hideProgress();
        }
        loginView.toRegisterInfo();
    }
}
