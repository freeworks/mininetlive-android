package com.kouchen.mininetlive.contracts;

import cn.sharesdk.framework.Platform;

import com.kouchen.mininetlive.BasePresenter;
import com.kouchen.mininetlive.BaseView;

/**
 * Created by cainli on 16/7/14.
 */
public interface AuthContract {

    interface View extends BaseView<Presenter> {
        void showInviteView();

        void showProgress(String msg);
    }

    interface Presenter extends BasePresenter {

        void login(String phone, String password);

        void validateCredentials(Platform sina, String... args);

        void getVCode(String phone, boolean b);

        void register(String mPhone, String mVCode, String mPassword, String mInviteCode, String name, int gender);

        void checkPhone(String mPhone, String phone);

        void postInviteCode(String inviteCode);

        void resetPassword(String p, String vcode, String pwd);
    }
}
