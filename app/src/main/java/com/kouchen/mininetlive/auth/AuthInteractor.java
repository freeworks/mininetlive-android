package com.kouchen.mininetlive.auth;

import cn.sharesdk.framework.Platform;

public interface AuthInteractor {

    interface OnLoginFinishedListener {

        void onSuccess();

        void onError(String msg);

        void onRegisting();

        void onRegisterSuccess();
    }

    void oauthLogin(final OnLoginFinishedListener listener, Platform platform, String... params);

    void login(final OnLoginFinishedListener listener, String phone, String password);

    void getVCode(final OnLoginFinishedListener listener, String phone);

    void register(final OnLoginFinishedListener listener, String phone, String vcode, String password, String inviteCode);

}
