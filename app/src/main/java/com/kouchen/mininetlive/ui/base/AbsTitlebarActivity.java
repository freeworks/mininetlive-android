package com.kouchen.mininetlive.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.kouchen.mininetlive.R;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_titlebar);
        rootContainer = (ViewGroup) findViewById(R.id.rootContainer);
        rootContainer.addView(getContentView());
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
