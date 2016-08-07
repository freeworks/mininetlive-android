package com.kouchen.mininetlive.contracts;

import com.kouchen.mininetlive.BasePresenter;
import com.kouchen.mininetlive.BaseView;
import com.kouchen.mininetlive.models.ActivityInfo;
import com.kouchen.mininetlive.models.HomeModel;

import java.util.List;

/**
 * Created by cainli on 16/7/14.
 */
public interface HomeContract {

    interface View extends BaseView<Presenter> {

        void onLoadMoreSuccess(List<ActivityInfo> activityInfos, boolean hasmore);

        void onLoadSuccess(HomeModel homeModel);

        void onRefreshError(String msg);

        void onLoadMoreError(String msg);
    }

    interface Presenter extends BasePresenter {

        void refresh();

        void initLoad();

        void loadMore(String lastId);
    }
}
