package com.kouchen.mininetlive.ui;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.contracts.AuthContract;
import com.kouchen.mininetlive.presenter.AuthPresenter;
import com.kouchen.mininetlive.ui.base.AbsTitlebarActivity;
import com.kouchen.mininetlive.utils.ValidateUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by cainli on 16/7/23.
 */
public class ResetPasswordActivity extends AbsTitlebarActivity implements AuthContract.View {

    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.sendVCode)
    TextView sendVCode;
    @BindView(R.id.vCode)
    EditText vCode;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.okbtn)
    TextView okBtn;

    @Inject
    AuthPresenter mAuthPresenter;

    private CountDownTimer downTimer;

    @Override
    protected void initView(View contentView) {
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
        return R.layout.activity_reset_password;
    }

    @Override
    public String getTitleString() {
        return "重置密码";
    }

    @OnClick(R.id.sendVCode)
    public void getVCode() {
        String p = phone.getText().toString();
        if (!ValidateUtil.checkPhone(p)) {
            Toast.makeText(this, "请输入正确的手机号码!", Toast.LENGTH_SHORT).show();
            return;
        }
        downTimer.cancel();
        downTimer.start();
        mAuthPresenter.getVCode(p,true);
    }


    @OnClick(R.id.okbtn)
    public void onOkClick() {
        String p = phone.getText().toString();
        String vcode = vCode.getText().toString();
        String pwd = password.getText().toString();
        if (ValidateUtil.checkPhone(p)) {
            if (ValidateUtil.checkVCode(vcode)) {
                if (ValidateUtil.checkPassword(vcode)) {
                    mAuthPresenter.resetPassword(p, vcode, pwd);
                } else {
                    Toast.makeText(this, "密码格式错误", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "验证码格式错误", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "手机号不能空", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showInviteView() {

    }

    @Override
    public void showProgress(String msg) {
    }


    @Override
    public void showProgress() {
        showProgressView("正在重置中...");
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
        Toast.makeText(this, "重置密码成功!", Toast.LENGTH_SHORT).show();
    }
}