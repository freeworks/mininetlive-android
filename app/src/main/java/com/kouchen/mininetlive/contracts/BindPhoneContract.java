package com.kouchen.mininetlive.contracts;

import com.kouchen.mininetlive.BasePresenter;
import com.kouchen.mininetlive.BaseView;

import cn.sharesdk.framework.Platform;

/**
 * Created by cainli on 16/7/14.
 */
public interface BindPhoneContract {

    interface View extends BaseView<Presenter> {

        void onBindSuccess();
    }

    interface Presenter extends BasePresenter {

        void getVCode(String phone, boolean b);

        void resetOrBindPhone(final String phone, String vcode);
    }
}
