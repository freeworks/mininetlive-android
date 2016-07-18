package com.kouchen.mininetlive.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.OnClick;

import com.kouchen.mininetlive.presenter.AuthPresenter;
import com.kouchen.mininetlive.ui.base.AbsTitlebarActivity;
import com.kouchen.mininetlive.MNLApplication;
import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.di.components.DaggerAuthComponent;
import com.kouchen.mininetlive.contracts.AuthContract;
import com.kouchen.mininetlive.di.modules.AuthModule;
import com.kouchen.mininetlive.utils.ValidateUtil;

import javax.inject.Inject;

/**
 * Created by cainli on 16/6/25.
 */
public class RegisterStep1Activity extends AbsTitlebarActivity implements AuthContract.View {

    @BindView(R.id.phone)
    EditText phone;

    @Inject
    AuthPresenter presenter;

    String  mPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerAuthComponent.builder()
            .authModule(new AuthModule(this))
            .netComponent(((MNLApplication) getApplication()).getNetComponent())
            .build()
            .inject(this);
    }

    @Override
    protected void initView(View contentView) {

    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_register_phone;
    }

    @Override
    public String getTitleString() {
        return "注册";
    }

    @OnClick(R.id.next)
    public void onClick(View view) {
        if (R.id.next == view.getId()) {
            mPhone = this.phone.getText().toString().trim();
            if(ValidateUtil.checkPhone(mPhone)){
                presenter.checkPhone(mPhone);
            }else{
                Toast.makeText(this,"请输入正确的手机号码",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void showProgress() {
        showProgressView();
    }

    @Override
    public void hideProgress() {
        hideProgressView();
    }

    @Override
    public void onError(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(Object data) {
        Intent intent = new Intent(this, RegisterStep2Activity.class);
        intent.putExtra("phone", mPhone);
        startActivity(intent);
    }

}
