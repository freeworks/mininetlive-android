package com.kouchen.mininetlive.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.kouchen.mininetlive.MNLApplication;
import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.contracts.AuthContract;
import com.kouchen.mininetlive.di.components.DaggerAuthComponent;
import com.kouchen.mininetlive.di.modules.AuthModule;
import com.kouchen.mininetlive.presenter.AuthPresenter;
import com.kouchen.mininetlive.ui.base.AbsTitlebarActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by cainli on 16/6/26.
 */
public class InputInviteCodeActivity extends AbsTitlebarActivity implements AuthContract.View {


    @BindView(R.id.inviteCode)
    EditText inviteCode;
    @BindView(R.id.finishBtn)
    TextView finishBtn;

    @Inject
    AuthPresenter mPresenter;

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
        titlebarView.setBackLister(null);
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_invite_input;
    }

    @Override
    public String getTitleString() {
        return "注册";
    }

    @OnClick(R.id.finishBtn)
    public void postInviteCode(){
        mPresenter.postInviteCode(inviteCode.getText().toString());
    }

    @Override
    public void showInviteView() {

    }

    @Override
    public void showProgress(String msg) {

    }

    @Override
    public void showProgress() {
        showProgressView("提交中...");
    }

    @Override
    public void hideProgress() {
        hideProgressView();
    }

    @Override
    public void onError(String msg) {
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSuccess(Object data) {
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}