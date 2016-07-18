package com.kouchen.mininetlive.ui;

import android.view.View;

import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.ui.base.AbsTitlebarActivity;

/**
 * Created by cainli on 16/6/26.
 */
public class AboutActivity extends AbsTitlebarActivity {


    @Override
    protected void initView(View contentView) {

    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_about;
    }

    @Override
    public String getTitleString() {
        return "关于微网";
    }
}
