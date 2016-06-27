package com.kouchen.mininetlive.base;

import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;

/**
 * Created by cainli on 16/6/21.
 */
public class BaseActivity extends FragmentActivity {

    public boolean isLogin() {
        SharedPreferences sp = getSharedPreferences("account", 0);
        return sp.getBoolean("isLogin",false);
    }
}
