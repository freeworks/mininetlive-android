package com.kouchen.mininetlive.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.kouchen.mininetlive.MNLApplication;
import com.kouchen.mininetlive.contracts.AccountContract;
import com.kouchen.mininetlive.di.components.DaggerAccountComponent;
import com.kouchen.mininetlive.di.modules.AccountModule;
import com.kouchen.mininetlive.presenter.AccountPresenter;
import com.kouchen.mininetlive.ui.base.AbsTitlebarActivity;
import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.models.UserInfo;
import com.kouchen.mininetlive.utils.ValidateUtil;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by cainli on 16/6/24.
 */
public class EditNickActivity extends AbsTitlebarActivity implements AccountContract.View {

    @BindView(R.id.nickname)
    EditText nickname;

    @Inject
    AccountPresenter mPresenter;

    private UserInfo userInfo;

    @Override
    protected void initInject() {
        DaggerAccountComponent.builder()
                .accountModule(new AccountModule(this))
                .netComponent(((MNLApplication) getApplication()).getNetComponent())
                .build()
                .inject(this);
    }

    @Override
    protected void initView(View contentView) {
        userInfo = getUserInfo();
        titlebarView.setRightTextView("保存", this);
        if (userInfo != null) {
            nickname.setText(userInfo.getNickname());
        }
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_me_edit_nickname;
    }

    @Override
    public String getTitleString() {
        return "昵称";
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.right) {
            String name = nickname.getText().toString();
            if (!nickname.equals(userInfo.getNickname())) {
                mPresenter.updateNickname(name);
            }
        } else if (view.getId() == R.id.clear) {
            nickname.setText("");
        }
    }

    @Override
    public void showProgress() {
        showProgressView("更新昵称中...");
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
        Toast.makeText(this, "更新成功", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra("nickname", nickname.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }
}
