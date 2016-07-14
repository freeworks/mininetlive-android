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

package com.kouchen.mininetlive.ui;

import android.content.Intent;
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
import com.kouchen.mininetlive.MNLApplication;
import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.ui.base.BaseActivity;
import com.kouchen.mininetlive.di.components.DaggerAuthComponent;
import com.kouchen.mininetlive.contracts.AuthContract;
import com.kouchen.mininetlive.di.modules.AuthModule;
import com.kouchen.mininetlive.presenter.AuthPresenter;
import javax.inject.Inject;

public class LoginActivity extends BaseActivity implements AuthContract.View, View.OnClickListener {

    private View progressBar;
    private EditText username;
    private EditText password;
    private EditText vCode;

    @Inject
    AuthPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressBar = findViewById(R.id.progress);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        findViewById(R.id.tvWechat).setOnClickListener(this);
        findViewById(R.id.tvWeibo).setOnClickListener(this);
        findViewById(R.id.tvQQ).setOnClickListener(this);
        findViewById(R.id.loginBtn).setOnClickListener(this);

        DaggerAuthComponent.builder()
            .authModule(new AuthModule(this))
            .netComponent(((MNLApplication) getApplication()).getNetComponent())
            .build()
            .inject(this);

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
    public void onError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(Object data) {
        setResult(RESULT_OK);
        finish();
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
