package com.kouchen.mininetlive.ui;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.ui.base.AbsTitlebarActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by cainli on 16/6/26.
 */
public class InviteCodeActivity extends AbsTitlebarActivity {


    @BindView(R.id.inviteCode)
    TextView inviteCode;

    @Override
    protected void initView(View contentView) {
        inviteCode.setText(getIntent().getStringExtra("inviteCode"));
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_invite;
    }

    @Override
    public String getTitleString() {
        return "我的邀请码";
    }

    @OnClick(R.id.inviteCode)
    public void onCopy() {
        ClipboardManager cmb = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            cmb.setPrimaryClip((ClipData.newPlainText(null, inviteCode.getText().toString())));
        } else {
            cmb.setText(inviteCode.getText().toString());
        }
        Toast.makeText(this,"复制成功!",Toast.LENGTH_SHORT).show();
    }
}