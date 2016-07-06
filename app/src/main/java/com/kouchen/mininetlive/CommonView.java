package com.kouchen.mininetlive;

/**
 * Created by cainli on 16/7/6.
 */
public interface CommonView {

    void showProgress();

    void hideProgress();

    void onFail();

    void success(Object object);
}
