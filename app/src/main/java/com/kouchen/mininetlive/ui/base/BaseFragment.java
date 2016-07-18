package com.kouchen.mininetlive.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.models.UserInfo;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by cainli on 16/6/21.
 */
public abstract class BaseFragment extends Fragment implements View.OnTouchListener {

    private View rootView;

    private Unbinder bind;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = getRootView();
        rootView.setBackgroundResource(R.color.background);
        rootView.setOnTouchListener(this);
        bind = ButterKnife.bind(this, rootView);
        initView(rootView);
        return rootView;
    }

    protected abstract void initView(View rootView);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        bind.unbind();
    }

    protected UserInfo getUserInfo() {
        FragmentActivity activity = getActivity();
        if (activity instanceof BaseActivity) {
            return ((BaseActivity) activity).getUserInfo();
        }
        return null;
    }

    public void hide() {
        if(rootView == null){
            return;
        }
        rootView.setVisibility(View.GONE);
    }

    public void show() {
        if(rootView == null){
            return;
        }
        rootView.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return true;
    }

    public abstract View getRootView();
}
