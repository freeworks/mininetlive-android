package com.kouchen.mininetlive.activity;


import java.util.List;

/**
 * Created by cainli on 16/6/25.
 */
public class ActivityPresenterImpl implements ActivityPresenter, ActivityInteractor.OnActivityFinishedListener {

    private LiveView liveView;
    private ActivityInteractor activityInteractor;

    public ActivityPresenterImpl(LiveView loginView) {
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
    public void onSuccess(Object object) {
        if(liveView!=null){
            liveView.success(object);
        }
    }

    @Override
    public void onError(String msg) {

    }
}
