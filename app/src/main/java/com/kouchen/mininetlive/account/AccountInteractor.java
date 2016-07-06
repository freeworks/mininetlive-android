package com.kouchen.mininetlive.account;

public interface AccountInteractor {

    interface OnAccountFinishedListener {

        void onSuccess(Object object);

        void onError(String msg);
    }

    void getPlayRecordList(final OnAccountFinishedListener listener);

    void getPayRecordList(final OnAccountFinishedListener listener);

    void getAppointRecordList(final OnAccountFinishedListener listener);

    void getAccountInfo(final OnAccountFinishedListener listener);

}
