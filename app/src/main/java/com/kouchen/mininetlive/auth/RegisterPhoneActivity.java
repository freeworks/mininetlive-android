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
import butterknife.OnClick;

/**
 * Created by cainli on 16/6/25.
 */
public class RegisterPhoneActivity extends AbsTitlebarActivity {

    @BindView(R.id.phone)
    EditText phone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
}
