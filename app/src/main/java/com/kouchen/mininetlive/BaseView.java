package com.kouchen.mininetlive;

/**
 * Created by cainli on 16/7/14.
 */
public interface BaseView<T> {

    void showProgress();

    void hideProgress();

    void onError(String msg);

    void onSuccess(Object data);
}
