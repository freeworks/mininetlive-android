package com.kouchen.mininetlive;

import com.kouchen.mininetlive.activity.ActivityInfo;
import com.kouchen.mininetlive.activity.HomeModel;

import java.util.List;

/**
 * Created by cainli on 16/6/25.
 */
public interface ActivityView extends CommonView {

    void getHomeListSuccess(HomeModel homeModel);

    void loadMoreSuccess(List<ActivityInfo> list, boolean hasmore);

    void getLiveListSuccess(List<ActivityInfo> list);
}
