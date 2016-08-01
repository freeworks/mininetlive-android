package com.kouchen.mininetlive.models;

import com.google.gson.annotations.SerializedName;

import com.kouchen.mininetlive.models.ActivityInfo;
import java.io.Serializable;
import java.util.List;

/**
 * Created by cainli on 16/6/26.
 */
public class HomeModel implements Serializable {

    @SerializedName("hasmore")
    public boolean hasMore;

    public List<ActivityInfo> recommend;

    public List<ActivityInfo> general;


    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public int getCount() {
        return recommondItemSize() + generalItemSize();
    }

    public int recommondItemSize() {
        return recommend == null ? 0 : recommend.size();
    }

    public int generalItemSize() {
        return general == null ? 0 : (general.size() / 2 + general.size() % 2);
    }


    public ActivityInfo getRecommend(int position) {
        if (recommend == null) {
            return null;
        } else {
            return recommend.get(position);
        }
    }


    public int getRecommondCount() {
        if (recommend == null) {
            return 0;
        }
        return recommend.size();
    }

    public ActivityInfo[] getGeneral(int position) {
        if (general == null) {
            return null;
        }
        ActivityInfo[] infos = new ActivityInfo[2];
        int startIndex = position * 2;
        infos[0] = general.get(startIndex);
        int nextIndex;
        if ((nextIndex = startIndex + 1) < general.size()) {
            infos[1] = general.get(nextIndex);
        }
        return infos;
    }
}
