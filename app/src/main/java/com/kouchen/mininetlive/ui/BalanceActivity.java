package com.kouchen.mininetlive.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.kouchen.mininetlive.MNLApplication;
import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.contracts.AccountContract;
import com.kouchen.mininetlive.di.components.DaggerAccountComponent;
import com.kouchen.mininetlive.di.modules.AccountModule;
import com.kouchen.mininetlive.presenter.AccountPresenter;
import com.kouchen.mininetlive.ui.base.AbsTitlebarActivity;

import java.text.DecimalFormat;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Created by cainli on 16/6/26.
 */
public class BalanceActivity extends AbsTitlebarActivity implements AccountContract.View {


    @BindView(R.id.balance)
    TextView balance;
    @BindView(R.id.withdrawBtn)
    TextView withdrawBtn;

    @Inject
    AccountPresenter presenter;

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
        titlebarView.setRightTextView("提现明细", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BalanceActivity.this, WithdrawRecordActivity.class);
                startActivity(intent);
            }
        });
        netErrView.setup(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.getWithdrawRecordList();
            }
        });
        showProgress();
        presenter.getBalance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.getBalance();
        }
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
        showProgressView("正在获取余额.." );
    }

    @Override
    public void hideProgress() {
        hideProgressView();
    }

    @Override
    public void onError(String msg) {
        hideProgress();
        balance.setText("--.--" );
        withdrawBtn.setEnabled(false);
    }

    @Override
    public void onSuccess(Object data) {
        hideProgress();
        withdrawBtn.setEnabled(true);
        DecimalFormat myformat = new DecimalFormat();
        myformat.applyPattern("##,##0.00");
        balance.setText(myformat.format(data));
    }
}
