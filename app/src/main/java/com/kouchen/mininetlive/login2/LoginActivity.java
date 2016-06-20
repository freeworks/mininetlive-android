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
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import com.kouchen.mininetlive.R;


public class LoginActivity extends Activity implements LoginView, View.OnClickListener {

    private ProgressBar progressBar;
    private EditText username;
    private EditText password;
    private EditText vCode;
    private LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        progressBar = (ProgressBar) findViewById(R.id.progress);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        findViewById(R.id.button).setOnClickListener(this);
        findViewById(R.id.tvWeibo).setOnClickListener(this);
        findViewById(R.id.tvQQ).setOnClickListener(this);
        findViewById(R.id.get_vcode_btn).setOnClickListener(this);
        findViewById(R.id.submit_vcode_btn).setOnClickListener(this);
        vCode = (EditText) findViewById(R.id.vcode_et);

        presenter = new LoginPresenterImpl(this);

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
    public void setUsernameError() {
        username.setError(getString(R.string.username_error));
    }

    @Override
    public void setPasswordError() {
        password.setError(getString(R.string.password_error));
    }

    @Override
    public void navigateToHome() {
//        startActivity(new Intent(this, MainActivity.class));
//        finish();
    }

    @Override
    public void setError(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSubmitVCodeSuccess() {
        Toast.makeText(this,"提交验证码成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetVCodeSuccess() {
        Toast.makeText(this,"获取验证码成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvWeibo) {
            Platform sina = ShareSDK.getPlatform(SinaWeibo.NAME);
            presenter.validateCredentials(sina);
        } else if (v.getId() == R.id.tvQQ) {
            Platform qq = ShareSDK.getPlatform(QQ.NAME);
            presenter.validateCredentials(qq);
        } else if(v.getId() == R.id.get_vcode_btn){
            presenter.sendMSM("18689490100");
        } else if(v.getId() == R.id.submit_vcode_btn){
            presenter.commmitSMS("18689490100",vCode.getText().toString());
        } else if(v.getId() == R.id.button){
            presenter.validateCredentials(null, username.getText().toString(),
                password.getText().toString());
        }

    }

}
