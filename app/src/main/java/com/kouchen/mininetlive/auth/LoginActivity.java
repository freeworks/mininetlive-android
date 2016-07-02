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

package com.kouchen.mininetlive.auth;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.wechat.friends.Wechat;

import com.kouchen.mininetlive.MainActivity;
import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.base.BaseActivity;


public class LoginActivity extends BaseActivity implements AuthView, View.OnClickListener {

    private ProgressBar progressBar;
    private EditText username;
    private EditText password;
    private EditText vCode;
    private AuthPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        findViewById(R.id.tvWechat).setOnClickListener(this);
        findViewById(R.id.tvWeibo).setOnClickListener(this);
        findViewById(R.id.tvQQ).setOnClickListener(this);
        findViewById(R.id.loginBtn).setOnClickListener(this);
        presenter = new AuthPresenterImpl(this);

    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void navigateToHome() {
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void setError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSubmitVCodeSuccess() {
        Toast.makeText(this, "提交验证码成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetVCodeSuccess() {
        Toast.makeText(this, "获取验证码成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toRegisterInfo() {
    }

    @Override
    public void onClick(View v) {
        Platform platform;
        switch (v.getId()) {
            case R.id.close:
                finish();
                break;
            case R.id.loginBtn:
                presenter.login(username.getText().toString(),
                        password.getText().toString());
                break;
            case R.id.reigsterBtn:
                Intent intent = new Intent(this,RegisterPhoneActivity.class);
                startActivity(intent);
                break;
            case R.id.tvWeibo:
                platform = ShareSDK.getPlatform(SinaWeibo.NAME);
                presenter.validateCredentials(platform);
                break;
            case R.id.tvQQ:
                platform = ShareSDK.getPlatform(QQ.NAME);
                presenter.validateCredentials(platform);
                break;
            case R.id.tvWechat:
                platform = ShareSDK.getPlatform(Wechat.NAME);
                presenter.validateCredentials(platform);
                break;
        }
    }
}
