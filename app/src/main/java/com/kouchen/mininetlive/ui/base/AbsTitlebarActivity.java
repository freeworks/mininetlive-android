package com.kouchen.mininetlive.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.ui.widget.NetErrorView;
import com.kouchen.mininetlive.ui.widget.NoDataView;
import com.kouchen.mininetlive.ui.widget.PanelView;
import com.kouchen.mininetlive.ui.widget.ProgressView;
import com.kouchen.mininetlive.ui.widget.TitlebarView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cainli on 16/6/25.
 */
public abstract class AbsTitlebarActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.titlebar)
    protected TitlebarView titlebarView;
    @BindView(R.id.rootContainer)
    protected ViewGroup rootContainer;
    @BindView(R.id.progressView)
    protected ProgressView progressView;
    @BindView(R.id.netErrView)
    protected NetErrorView netErrView;
    @BindView(R.id.noDateView)
    protected NoDataView noDateView;
    @BindView(R.id.panelView)
    protected PanelView panelView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_titlebar);
        rootContainer = (ViewGroup) findViewById(R.id.rootContainer);
        rootContainer.addView(getContentView(), rootContainer.getChildCount() - 2);
        ButterKnife.bind(this);
        titlebarView.setBackLister(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        titlebarView.setTitle(getTitleString());
        if (isBelowTitleBar()) {
            ((RelativeLayout.LayoutParams) rootContainer.getLayoutParams()).topMargin =
                    (int) getResources().getDimension(R.dimen.titlebar_height);
        }
        initView(getContentView());
    }

    protected abstract void initView(View contentView);

    protected void showProgressView(String msg) {
        progressView.setText(msg);
        progressView.setVisibility(View.VISIBLE);
    }

    protected void hideProgressView() {
        progressView.setVisibility(View.INVISIBLE);
    }

    protected View getContentView() {
        return LayoutInflater.from(this).inflate(getContentResId(), null);
    }

    protected abstract int getContentResId();

    public abstract String getTitleString();

    protected boolean isBelowTitleBar() {
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back) {
            finish();
        }
    }
}
