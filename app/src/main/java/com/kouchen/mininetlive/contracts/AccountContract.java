package com.kouchen.mininetlive.contracts;

import com.kouchen.mininetlive.BasePresenter;
import com.kouchen.mininetlive.BaseView;

/**
 * Created by cainli on 16/7/14.
 */
public interface AccountContract {

    interface View extends BaseView<Presenter> {

    }

    interface Presenter extends BasePresenter {

        void getWithdrawRecordList();

        void getPlayRecordList();

        void getPayRecordList();

        void getAppointRecordList();

        void getAccountInfo();

        void uploadAvatar(String filepath);

        void updateNickname(String name);

    }
}
