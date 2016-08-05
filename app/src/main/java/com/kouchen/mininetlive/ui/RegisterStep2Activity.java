package com.kouchen.mininetlive.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kouchen.mininetlive.MNLApplication;
import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.contracts.AuthContract;
import com.kouchen.mininetlive.di.components.DaggerAuthComponent;
import com.kouchen.mininetlive.di.modules.AuthModule;
import com.kouchen.mininetlive.presenter.AuthPresenter;
import com.kouchen.mininetlive.ui.base.AbsTitlebarActivity;
import com.kouchen.mininetlive.utils.ValidateUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by cainli on 16/6/25.
 */
public class RegisterStep2Activity extends AbsTitlebarActivity implements AuthContract.View {

    @BindView(R.id.phone)
    TextView phone;
    @BindView(R.id.vCode)
    EditText vcodeEt;
    @BindView(R.id.password)
    EditText passwordEt;
    @BindView(R.id.inviteCode)
    EditText inviteCodeEt;
    @BindView(R.id.sendVCode)
    TextView sendVCode;

    private String mPhone;

    private CountDownTimer downTimer;

    private  String vcode;
    private String password;
    private  String inviteCode;

    @Inject
    AuthPresenter presenter;

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
        mPhone = getIntent().getStringExtra("phone");
        phone.setText(mPhone);
        downTimer = new CountDownTimer(60_000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                sendVCode.setClickable(false);
                sendVCode.setText("剩余" + millisUntilFinished / 1000 + "秒");
            }

            @Override
            public void onFinish() {
                sendVCode.setClickable(true);
                sendVCode.setText("再次获取");
            }
        };
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_register_password;
    }

    @Override
    public String getTitleString() {
        return "注册";
    }


    @OnClick(R.id.registerBtn)
    public void onClick(View view) {
        vcode = this.vcodeEt.getText().toString();
        password = passwordEt.getText().toString();
        inviteCode = inviteCodeEt.getText().toString();
        if (ValidateUtil.checkVCode(vcode)) {
            if (ValidateUtil.checkPassword(password)) {
                presenter.checkPhone(mPhone,vcode);
            } else {
                Toast.makeText(this, "输入的6-18位只包含字母和数字组成的密码", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "输入6位正确的验证码", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showProgress() {
        showProgressView("验证中...");
    }

    @Override
    public void hideProgress() {
        hideProgressView();
    }

    @Override
    public void onError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.sendVCode)
    public void getVCode() {
        downTimer.cancel();
        downTimer.start();
        presenter.getVCode(mPhone,true);
    }

    @Override
    public void onSuccess(Object data) {
        if("checkPhone".equals(data)){
            Intent intent = new Intent(this, RegisterStep3Activity.class);
            intent.putExtra("phone", mPhone);
            intent.putExtra("vcode", inviteCode);
            intent.putExtra("password", password);
            intent.putExtra("inviteCode", inviteCode);
            startActivity(intent);
        }
    }

    @Override
    public void showInviteView() {

    }

    @Override
    public void showProgress(String msg) {

    }
}
