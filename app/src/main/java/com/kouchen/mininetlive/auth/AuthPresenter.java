package com.kouchen.mininetlive.auth;


import cn.sharesdk.framework.Platform;

public interface AuthPresenter {

    void onDestroy();

    void login(String phone, String password);

    void validateCredentials(Platform sina, String... args);

    void getVCode(String phone);

    void register(String phone, String vcode, String password, String inviteCode);
}
