package com.kouchen.mininetlive.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.kouchen.mininetlive.ui.base.AbsTitlebarActivity;
import com.kouchen.mininetlive.R;

/**
 * Created by cainli on 16/6/26.
 */
public class InviteCodeActivity extends AbsTitlebarActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_invite;
    }

    @Override
    public String getTitleString() {
        return "我的邀请码";
    }
}