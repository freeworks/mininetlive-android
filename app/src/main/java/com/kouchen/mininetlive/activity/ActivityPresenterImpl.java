package com.kouchen.mininetlive.activity;

import com.kouchen.mininetlive.ActivityView;

import java.util.List;

/**
 * Created by cainli on 16/6/25.
 */
public class ActivityPresenterImpl implements ActivityPresenter, ActivityInteractor.OnActivityFinishedListener {

    private ActivityView liveView;
    private ActivityInteractor activityInteractor;

    public ActivityPresenterImpl(ActivityView loginView) {
        this.liveView = loginView;
        this.activityInteractor = new ActivityInteractorImpl();
    }

    @Override
    public void onDestroy() {
        liveView = null;
    }

    @Override
    public void getLiveList() {
        if (liveView != null) {
            liveView.showProgress();
        }
        activityInteractor.getLiveList(this);
    }

    @Override
    public void getHomeList() {
        if (liveView != null) {
            liveView.showProgress();
        }
        activityInteractor.getHomeList(this);
    }

    @Override
    public void loadMore(String lastId) {
        activityInteractor.getMore(this,lastId);
    }


    @Override
    public void getHomeListSuccess(HomeModel homeModel) {
        if (liveView != null) {
            liveView.hideProgress();
        }
        liveView.getHomeListSuccess(homeModel);
    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public void onLoadMoreSuccess(List<ActivityInfo> list, boolean hasmore) {
        if (liveView != null) {
            liveView.hideProgress();
        }
        liveView.loadMoreSuccess(list,hasmore);
    }

    @Override
    public void onGetLiveListSuccess(List<ActivityInfo> list) {
        if (liveView != null) {
            liveView.hideProgress();
        }
        liveView.getLiveListSuccess(list);
    }
}
