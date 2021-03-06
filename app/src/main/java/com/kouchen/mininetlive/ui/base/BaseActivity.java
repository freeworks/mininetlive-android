package com.kouchen.mininetlive.ui.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import com.google.gson.reflect.TypeToken;
import com.kouchen.mininetlive.Constants;
import com.kouchen.mininetlive.MNLApplication;
import com.kouchen.mininetlive.models.UserInfo;
import com.kouchen.mininetlive.ui.LoginActivity;
import com.umeng.message.PushAgent;
import java.lang.reflect.Type;

/**
 * Created by cainli on 16/6/21.
 */
public class BaseActivity extends FragmentActivity {

    protected final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PushAgent.getInstance(this).onAppStart();
    }

    public boolean isLogin() {

        return getUserInfo() != null;
    }

    public UserInfo getUserInfo() {
        Type userType = new TypeToken<UserInfo>() {
        }.getType();
        UserInfo userInfo = (UserInfo) MNLApplication.getCacheManager().get("user", UserInfo.class, userType);
        return userInfo;
    }

    public void showLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivityForResult(intent, Constants.RequestCode.LOGIN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==  Constants.RequestCode.LOGIN){
            handleLogin(resultCode,data);
        }
    }

    protected void handleLogin(int resultCode, Intent data) {

    }
}
