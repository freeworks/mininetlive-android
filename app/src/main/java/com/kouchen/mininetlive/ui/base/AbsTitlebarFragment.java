package com.kouchen.mininetlive.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.ui.widget.NetErrorView;
import com.kouchen.mininetlive.ui.widget.NoDataView;
import com.kouchen.mininetlive.ui.widget.ProgressView;
import com.kouchen.mininetlive.ui.widget.TitlebarView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cainli on 16/6/25.
 */
public abstract class AbsTitlebarFragment extends BaseFragment {

    @BindView(R.id.titlebar)
    protected TitlebarView titlebar;
    @BindView(R.id.rootContainer)
    protected FrameLayout rootContainer;
    @BindView(R.id.netErrView)
    protected NetErrorView netErrView;
    @BindView(R.id.noDateView)
    protected NoDataView noDateView;
    @BindView(R.id.progressView)
    protected ProgressView progressView;


    @Override
    public View getRootView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_base_titlebar, null);
        ViewGroup container = (ViewGroup) view.findViewById(R.id.rootContainer);
        container.addView(getContentView());
        return view;
    }

    @Override
    protected void initView(View rootView) {
        titlebar.setTitle(getTitle());
        if (!canback()) {
            titlebar.setBackLister(null);
        } else {
            titlebar.setBackLister(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onback();
                }
            });
        }
    }

    protected void showProgressView() {
        progressView.setVisibility(View.VISIBLE);
    }

    protected void hideProgressView() {
        progressView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    protected abstract int getContentViewResId();

    protected View getContentView() {
        return LayoutInflater.from(getContext()).inflate(getContentViewResId(), null);
    }

    protected abstract String getTitle();

    protected boolean canback() {
        return false;
    }

    protected void onback() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
