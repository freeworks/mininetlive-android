package com.kouchen.mininetlive;

/**
 * Created by cainli on 16/6/26.
 */
public class AboutActivity extends AbsTitlebarActivity {


    @Override
    protected int getContentResId() {
        return R.layout.activity_about;
    }

    @Override
    public String getTitleString() {
        return "关于微网";
    }
}
