package com.kouchen.mininetlive.base;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;

import butterknife.ButterKnife;

/**
 * Created by cainli on 16/6/21.
 */
public class BaseActivity extends FragmentActivity {


    public boolean isLogin() {
        SharedPreferences sp = getSharedPreferences("account", 0);
        return sp.getBoolean("isLogin",false);
    }
}
