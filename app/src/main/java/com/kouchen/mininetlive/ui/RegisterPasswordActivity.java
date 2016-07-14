package com.kouchen.mininetlive.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import butterknife.BindView;
import com.kouchen.mininetlive.ui.base.AbsTitlebarActivity;
import com.kouchen.mininetlive.MNLApplication;
import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.di.components.DaggerAuthComponent;
import com.kouchen.mininetlive.contracts.AuthContract;
import com.kouchen.mininetlive.di.modules.AuthModule;
import com.kouchen.mininetlive.presenter.AuthPresenter;
import javax.inject.Inject;

/**
 * Created by cainli on 16/6/25.
 */
public class RegisterPasswordActivity extends AbsTitlebarActivity implements AuthContract.View {

    @BindView(R.id.vCode)
    EditText vcode;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.inviteCode)
    EditText inviteCode;

    private String phone;

    @Inject
    AuthPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerAuthComponent.builder()
            .authModule(new AuthModule(this))
            .netComponent(((MNLApplication) getApplication()).getNetComponent())
            .build()
            .inject(this);

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
    public void onError(String msg) {

    }

    @Override
    public void onSuccess() {
        Intent intent = new Intent(this, RegisterInfoActivity.class);
        startActivity(intent);
    }
}
