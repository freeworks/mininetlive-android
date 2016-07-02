package com.kouchen.mininetlive.base;

import android.support.v4.app.FragmentActivity;

import com.google.gson.reflect.TypeToken;
import com.kouchen.mininetlive.MNLApplication;
import com.kouchen.mininetlive.auth.UserInfo;

import java.lang.reflect.Type;

/**
 * Created by cainli on 16/6/21.
 */
public class BaseActivity extends FragmentActivity {


    public boolean isLogin() {

        return getUserInfo() != null;
    }

    public UserInfo getUserInfo() {
        Type userType = new TypeToken<UserInfo>() {
        }.getType();
        UserInfo userInfo = (UserInfo) MNLApplication.getCacheManager().get("user", UserInfo.class, userType);
        return userInfo;
    }
}
