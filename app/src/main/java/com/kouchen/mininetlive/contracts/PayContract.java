package com.kouchen.mininetlive.contracts;

import com.kouchen.mininetlive.BasePresenter;
import com.kouchen.mininetlive.BaseView;
import com.kouchen.mininetlive.ui.PayChannel;

/**
 * Created by cainli on 16/7/14.
 */
public interface PayContract {

    interface View extends BaseView<Presenter> {
    }

    interface Presenter extends BasePresenter {

        void pay(String aid, PayChannel channel, int count);

        void withdraw(int count);
    }
}
