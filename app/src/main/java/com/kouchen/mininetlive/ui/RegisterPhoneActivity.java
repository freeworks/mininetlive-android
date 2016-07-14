package com.kouchen.mininetlive.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.OnClick;
import com.kouchen.mininetlive.ui.base.AbsTitlebarActivity;
import com.kouchen.mininetlive.MNLApplication;
import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.di.components.DaggerAuthComponent;
import com.kouchen.mininetlive.contracts.AuthContract;
import com.kouchen.mininetlive.di.modules.AuthModule;

/**
 * Created by cainli on 16/6/25.
 */
public class RegisterPhoneActivity extends AbsTitlebarActivity implements AuthContract.View {

    @BindView(R.id.phone)
    EditText phone;

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

            Intent intent = new Intent(this, RegisterPasswordActivity.class);
            intent.putExtra("phone", phone.getText().toString());
            startActivity(intent);
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
    public void onSuccess(Object data) {

    }

}
