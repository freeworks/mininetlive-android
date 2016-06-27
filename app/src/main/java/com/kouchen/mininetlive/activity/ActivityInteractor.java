package com.kouchen.mininetlive.activity;

import java.util.List;

public interface ActivityInteractor {

    interface OnActivityFinishedListener {

        void onSuccess(Object object);

        void onError(String msg);
    }

    void getLiveList(final OnActivityFinishedListener listener);

    void getHomeList(final OnActivityFinishedListener listener);

}
