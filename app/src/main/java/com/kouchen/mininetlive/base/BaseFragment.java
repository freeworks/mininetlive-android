package com.kouchen.mininetlive.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.auth.UserInfo;

/**
 * Created by cainli on 16/6/21.
 */
public abstract class BaseFragment extends Fragment implements View.OnTouchListener {

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = getRootView();
        rootView.setBackgroundResource(R.color.background);
        rootView.setOnTouchListener(this);
        return rootView;
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