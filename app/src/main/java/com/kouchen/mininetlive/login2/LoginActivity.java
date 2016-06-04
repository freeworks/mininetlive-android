/*
 *
 *  * Copyright (C) 2014 Antonio Leiva Gordillo.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.kouchen.mininetlive.login2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.login.Tool;

import cn.sharesdk.framework.CustomPlatform;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;


public class LoginActivity extends Activity implements LoginView, View.OnClickListener {

    private ProgressBar progressBar;
    private EditText username;
    private EditText password;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = (ProgressBar) findViewById(R.id.progress);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        findViewById(R.id.button).setOnClickListener(this);

        presenter = new LoginPresenterImpl(this);

        initPlatformList();
    }

    @Override protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override public void setUsernameError() {
        username.setError(getString(R.string.username_error));
    }

    @Override public void setPasswordError() {
        password.setError(getString(R.string.password_error));
    }

    @Override public void navigateToHome() {
//        startActivity(new Intent(this, MainActivity.class));
//        finish();
    }

    @Override
    public void setError(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override public void onClick(View v) {
        Button btn = (Button) v;
        Object tag = v.getTag();
        if (tag != null) {
            Platform platform = (Platform) tag;
            String name = platform.getName();
            System.out.println("名字"+name+" "+getString(com.kouchen.mininetlive.R.string.login_to_format, name));
            if(!platform.isAuthValid()){
                btn.setText(getString(com.kouchen.mininetlive.R.string.remove_to_format, name));
            }else{
                btn.setText(getString(com.kouchen.mininetlive.R.string.login_to_format, name));
                String msg = getString(com.kouchen.mininetlive.R.string.remove_to_format_success, name);
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
            }
            presenter.validateCredentials(LoginType.PHONE,platform.getDb().getUserId(),platform.getDb().getToken());
        }else{
            presenter.validateCredentials(LoginType.PHONE,username.getText().toString(), password.getText().toString());
        }

    }

    /* 获取平台列表,显示平台按钮*/
    private void initPlatformList() {
        ShareSDK.initSDK(this);
        Platform[] Platformlist = ShareSDK.getPlatformList();
        if (Platformlist != null) {
            LinearLayout linear = (LinearLayout) findViewById(com.kouchen.mininetlive.R.id.linear);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            lp.weight = 1;
            for (Platform platform : Platformlist) {
                if (!Tool.canGetUserInfo(platform)) {
                    continue;
                }

                if (platform instanceof CustomPlatform) {
                    continue;
                }

                Button btn = new Button(this);
                btn.setSingleLine();
                String name = platform.getName();
                System.out.println("名字"+name);
                if(platform.isAuthValid()){
                    btn.setText(getString(com.kouchen.mininetlive.R.string.remove_to_format, name));
                }else{
                    btn.setText(getString(com.kouchen.mininetlive.R.string.login_to_format, name));
                }
                btn.setTextSize(16);
                btn.setTag(platform);
                btn.setVisibility(View.VISIBLE);
                btn.setOnClickListener(this);
                linear.addView(btn, lp);
            }
        }
    }

}
