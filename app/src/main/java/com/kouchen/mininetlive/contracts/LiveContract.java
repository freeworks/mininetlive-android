package com.kouchen.mininetlive.contracts;

import com.kouchen.mininetlive.BasePresenter;
import com.kouchen.mininetlive.BaseView;
import com.kouchen.mininetlive.models.ActivityInfo;

import java.util.List;

/**
 * Created by cainli on 16/7/14.
 */
public interface LiveContract {

    interface View extends BaseView<Presenter> {

        void onLoadSuccess(List<ActivityInfo> activityInfos);

        void onInitLoadError(String msg);

        void onRefreshError(String msg);
    }

    interface Presenter extends BasePresenter {

        void refresh();

        void initLoad();
    }
}
