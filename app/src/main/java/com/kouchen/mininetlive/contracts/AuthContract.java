package com.kouchen.mininetlive.contracts;

import cn.sharesdk.framework.Platform;
import com.kouchen.mininetlive.BasePresenter;
import com.kouchen.mininetlive.BaseView;

/**
 * Created by cainli on 16/7/14.
 */
public interface AuthContract {

    interface View extends BaseView<Presenter> {

        void showProgress();

        void hideProgress();

        void onError(String msg);

        void onSuccess();
    }

    interface Presenter extends BasePresenter {

        void login(String phone, String password);

        void validateCredentials(Platform sina, String... args);

        void getVCode(String phone);

        void register(String phone, String vcode, String password, String inviteCode);
    }
}
