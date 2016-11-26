package com.kouchen.mininetlive.contracts;

import com.kouchen.mininetlive.BasePresenter;
import com.kouchen.mininetlive.BaseView;
import com.kouchen.mininetlive.ui.PayChannel;

/**
 * Created by cainli on 16/7/14.
 */
public interface WithdrawContract {

    interface View extends BaseView<Presenter> {
        void showProgress(String s);

        void onUnbindPhone();

        void onUnbindWxPub();

        void onGetBalanceSuccess(long balance);

        void onGetBalanceError(String msg);
    }

    interface Presenter extends BasePresenter {

        void withdraw(int count);

        void getBalance();

        void refreshBalance();
    }
}
