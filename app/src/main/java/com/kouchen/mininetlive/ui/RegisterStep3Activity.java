package com.kouchen.mininetlive.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.kouchen.mininetlive.MNLApplication;
import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.contracts.AuthContract;
import com.kouchen.mininetlive.di.components.DaggerAuthComponent;
import com.kouchen.mininetlive.di.modules.AuthModule;
import com.kouchen.mininetlive.presenter.AuthPresenter;
import com.kouchen.mininetlive.ui.base.AbsTitlebarActivity;
import com.kouchen.mininetlive.ui.widget.GenderChooseView;
import com.kouchen.mininetlive.utils.ValidateUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by cainli on 16/6/23.
 */
public class RegisterStep3Activity extends AbsTitlebarActivity implements AuthContract.View {

    @Inject
    AuthPresenter mPresenter;

    @BindView(R.id.nickname)
    EditText nickname;
    @BindView(R.id.genderView)
    GenderChooseView genderView;

    String mPhone, mPassword, mInviteCode, mVCode;

    @Override
    protected void initInject() {
        DaggerAuthComponent.builder()
                .authModule(new AuthModule(this))
                .netComponent(((MNLApplication) getApplication()).getNetComponent())
                .build()
                .inject(this);
    }

    @Override
    protected void initView(View contentView) {
        Intent intent = getIntent();
        mPhone = intent.getStringExtra("phone");
        mVCode = intent.getStringExtra("vcode");
        mPassword = intent.getStringExtra("password");
        mInviteCode = intent.getStringExtra("inviteCode");
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_register_info;
    }

    @Override
    public String getTitleString() {
        return "设置个人信息";
    }


    @OnClick(R.id.registerBtn)
    public void onClick(View view) {
        String name = nickname.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "昵称不能为空!", Toast.LENGTH_SHORT).show();
            return;
        }
        mPresenter.register(mPhone, mVCode, mPassword, mInviteCode, name, genderView.getValue());
    }

    @Override
    public void showProgress() {
        showProgressView("注册中...");
    }

    @Override
    public void hideProgress() {
        hideProgressView();
    }

    @Override
    public void onError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(Object data) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void showInviteView() {
    }

    @Override
    public void showProgress(String msg) {

    }
}
