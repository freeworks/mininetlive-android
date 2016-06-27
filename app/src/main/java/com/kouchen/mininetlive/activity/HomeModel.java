package com.kouchen.mininetlive.activity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by cainli on 16/6/26.
 */
public class HomeModel implements Serializable {

    public List<ActivityInfo> recommond;

    public List<ActivityInfo> general;


    public int getCount() {
        return recommondItemSize() + generalItemSize();
    }

    public int recommondItemSize() {
        return recommond == null ? 0 : recommond.size();
    }

    public int generalItemSize() {
        return general == null ? 0 : (general.size() / 2 + general.size() % 2);
    }

    public ActivityInfo[] getActivityByItemIndex(int position) {
        if (position < recommondItemSize()) {
            return new ActivityInfo[]{recommond.get(position)};
        } else {
            int startIndex = (position - recommond.size()) * 2;
            ActivityInfo a1 = general.get(startIndex);
            ActivityInfo a2 = null;
            if (startIndex != general.size() - 1) {
               a2 = general.get(startIndex+1);
            }
            return new ActivityInfo[]{a1,a2};
        }
    }
}
