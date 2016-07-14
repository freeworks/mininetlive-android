package com.kouchen.mininetlive.contracts;

import com.kouchen.mininetlive.BasePresenter;
import com.kouchen.mininetlive.BaseView;
import com.kouchen.mininetlive.models.ActivityInfo;
import java.util.List;

/**
 * Created by cainli on 16/7/14.
 */
public interface ActivityContract {

    interface View extends BaseView<Presenter> {

        void showProgress();

        void hideProgress();

        void onError(String msg);

        void onInitLoadSuccess(Object data);

        void onLoadMoreSuccess(List<ActivityInfo> activityInfos, boolean hasmore);
    }

    interface Presenter extends BasePresenter {

        void getLiveList();

        void getHomeList();

        void loadMore(String lastId);

    }
}
