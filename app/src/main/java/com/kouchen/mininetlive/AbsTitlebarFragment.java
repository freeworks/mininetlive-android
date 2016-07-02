package com.kouchen.mininetlive;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kouchen.mininetlive.base.BaseFragment;
import com.kouchen.mininetlive.ui.TitlebarView;

import butterknife.ButterKnife;

/**
 * Created by cainli on 16/6/25.
 */
public abstract class AbsTitlebarFragment extends BaseFragment {

    protected TitlebarView titlebarView;

    @Override
    public View getRootView() {
        LinearLayout linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        titlebarView = new TitlebarView(getContext());
        titlebarView.setTitle(getTitle());
        if (!canback()) {
            titlebarView.setBackLister(null);
        } else {
            titlebarView.setBackLister(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onback();
                }
            });
        }
        linearLayout.addView(titlebarView);
        linearLayout.addView(getContentView());
        linearLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
        ButterKnife.bind(this, linearLayout);
        initView(linearLayout);
        return linearLayout;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    protected void initView(View view) {

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
}
