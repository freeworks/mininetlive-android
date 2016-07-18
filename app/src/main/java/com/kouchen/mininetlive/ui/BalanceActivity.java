package com.kouchen.mininetlive.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.kouchen.mininetlive.MNLApplication;
import com.kouchen.mininetlive.contracts.AccountContract;
import com.kouchen.mininetlive.di.components.DaggerAccountComponent;
import com.kouchen.mininetlive.di.components.DaggerAuthComponent;
import com.kouchen.mininetlive.di.modules.AccountModule;
import com.kouchen.mininetlive.ui.base.AbsTitlebarActivity;
import com.kouchen.mininetlive.R;

/**
 * Created by cainli on 16/6/26.
 */
public class BalanceActivity extends AbsTitlebarActivity implements AccountContract.View {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titlebarView.setRightTextView("提现明细", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BalanceActivity.this, WithdrawRecordActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initView(View contentView) {

    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_balance;
    }

    @Override
    public String getTitleString() {
        return "我的分红";
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
