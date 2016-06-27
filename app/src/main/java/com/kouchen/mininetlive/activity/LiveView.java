package com.kouchen.mininetlive.activity;

import java.util.List;

/**
 * Created by cainli on 16/6/25.
 */
public interface LiveView {

    void showProgress();

    void hideProgress();

    void success(Object list);
}
