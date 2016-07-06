package com.kouchen.mininetlive.activity;

import java.util.List;

public interface ActivityInteractor {

    interface OnActivityFinishedListener {

        void onError(String msg);

        void onLoadMoreSuccess(List<ActivityInfo> list, boolean hasmore);

        void onGetLiveListSuccess(List<ActivityInfo> list);

        void getHomeListSuccess(HomeModel homeModel);
    }

    void getLiveList(final OnActivityFinishedListener listener);

    void getHomeList(final OnActivityFinishedListener listener);

    void getMore(final OnActivityFinishedListener listener, String lastId);

}
