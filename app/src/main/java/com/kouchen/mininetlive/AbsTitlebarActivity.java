package com.kouchen.mininetlive;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.kouchen.mininetlive.base.BaseActivity;
import com.kouchen.mininetlive.ui.TitlebarView;

/**
 * Created by cainli on 16/6/25.
 */
public abstract class AbsTitlebarActivity extends BaseActivity implements View.OnClickListener {

    protected  TitlebarView titlebarView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout linearLayout = new LinearLayout(this);
        titlebarView = new TitlebarView(this);
        titlebarView.setOnClickListener(this);
        linearLayout.addView(titlebarView, new LinearLayout.LayoutParams(-1, -2));
        linearLayout.addView(getContentView(), new LinearLayout.LayoutParams(-1, -1));
        setContentView(linearLayout, new LinearLayout.LayoutParams(-1, -1));
    }

    protected abstract View getContentView();

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.back) {
            finish();
        }
    }
}
