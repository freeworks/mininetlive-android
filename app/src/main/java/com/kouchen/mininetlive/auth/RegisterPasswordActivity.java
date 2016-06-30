package com.kouchen.mininetlive.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.kouchen.mininetlive.AbsTitlebarActivity;
import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.base.BaseActivity;

import butterknife.BindView;

/**
 * Created by cainli on 16/6/25.
 */
public class RegisterPasswordActivity extends AbsTitlebarActivity implements AuthView {

    private AuthPresenterImpl presenter;

    @BindView(R.id.vCode)
    EditText vcode;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.inviteCode)
    EditText inviteCode;

    private String phone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new AuthPresenterImpl(this);
        phone = getIntent().getStringExtra("phone");
        presenter.getVCode(phone);
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_register_password;
    }

    @Override
    public String getTitleString() {
        return "注册";
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
        Intent intent = new Intent(this, RegisterInfoActivity.class);
        startActivity(intent);
    }
}
