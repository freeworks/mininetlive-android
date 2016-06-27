package com.kouchen.mininetlive.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.base.BaseActivity;

/**
 * Created by cainli on 16/6/25.
 */
public class RegisterPasswordActivity extends BaseActivity implements AuthView {

    private AuthPresenterImpl presenter;

    private EditText vcode, password, inviteCode;

    private String phone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_password);
        vcode = (EditText) findViewById(R.id.vCode);
        password = (EditText) findViewById(R.id.password);
        inviteCode = (EditText) findViewById(R.id.inviteCode);
        presenter = new AuthPresenterImpl(this);
        phone = getIntent().getStringExtra("phone");
        presenter.getVCode(phone);
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }


    public void onClick(View view) {
        if (R.id.next == view.getId()) {
            presenter.register(phone, vcode.getText().toString(), password.getText().toString(), inviteCode.getText().toString());
        }
    }


    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void navigateToHome() {

    }

    @Override
    public void setError(String msg) {

    }

    @Override
    public void onSubmitVCodeSuccess() {

    }

    @Override
    public void onGetVCodeSuccess() {

    }

    @Override
    public void toRegisterInfo() {
        Intent intent = new Intent(this,RegisterInfoActivity.class);
        startActivity(intent);
    }
}
