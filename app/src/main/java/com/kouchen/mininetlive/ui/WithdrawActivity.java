package com.kouchen.mininetlive.ui;

import android.content.Intent;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kouchen.mininetlive.MNLApplication;
import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.contracts.WithdrawContract;
import com.kouchen.mininetlive.di.components.DaggerWithdrawComponent;
import com.kouchen.mininetlive.di.modules.WithdrawModule;
import com.kouchen.mininetlive.presenter.WithdrawPresenter;
import com.kouchen.mininetlive.ui.base.AbsTitlebarActivity;
import com.kouchen.mininetlive.ui.dialog.WithdrawAlertDialog;

import java.text.DecimalFormat;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by cainli on 16/6/26.
 */
public class WithdrawActivity extends AbsTitlebarActivity implements WithdrawContract.View {

    @BindView(R.id.balance)
    TextView balance;
    @BindView(R.id.withdrawBtn)
    TextView withdrawBtn;
    @BindView(R.id.detail)
    TextView detail;
    @BindView(R.id.withdrawInput)
    EditText withdrawInput;

    @Inject
    WithdrawPresenter presenter;

    @Override
    protected void initInject() {
        DaggerWithdrawComponent.builder()
                .withdrawModule(new WithdrawModule(this))
                .netComponent(((MNLApplication) getApplication()).getNetComponent())
                .build()
                .inject(this);

    }

    @Override
    protected void initView(View contentView) {
        titlebarView.setRightTextView("邀请奖励", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WithdrawActivity.this, DividendListActivity.class);
                startActivity(intent);
            }
        });
        InputFilter[] filters = {new CashierInputFilter(8), new InputFilter.LengthFilter(8)};
        withdrawInput.setFilters(filters);


        presenter.getBalance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (presenter != null) {
            presenter.refreshBalance();
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
        showProgressView("正在获取余额..");
    }

    @Override
    public void hideProgress() {
        hideProgressView();
    }

    @Override
    public void onError(String msg) {
        Toast.makeText(this.getApplication(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(Object data) {
    }

    @OnClick(R.id.withdrawBtn)
    public void withdraw() {
        String amount = withdrawInput.getText().toString();
        if (!TextUtils.isEmpty(amount)) {
            presenter.withdraw(Integer.parseInt(amount.replace(".", "")) * 100);
        }
    }

    @OnClick(R.id.detail)
    public void detail(View view) {
        Intent intent = new Intent(WithdrawActivity.this, WithdrawRecordActivity.class);
        startActivity(intent);
    }

    @Override
    public void showProgress(String s) {
        showProgressView(s);
    }

    @Override
    public void onUnbindPhone() {
        Toast.makeText(this, "还没有绑定手机号!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(WithdrawActivity.this, BindPhoneActivity.class);
        startActivity(intent);
    }

    @Override
    public void onUnbindWxPub() {
        WithdrawAlertDialog dialog = new WithdrawAlertDialog(this);
        dialog.show();
    }

    @Override
    public void onGetBalanceSuccess(long amount) {
        hideProgress();
        withdrawBtn.setEnabled(true);
        DecimalFormat myformat = new DecimalFormat();
        myformat.applyPattern("##,###.00");
        balance.setText(myformat.format(amount / 100f));
        withdrawInput.setHint("可提现金额" + myformat.format(amount / 100f));
    }

    @Override
    public void onGetBalanceError(String msg) {
        hideProgress();
        balance.setText("--.--");
        withdrawBtn.setEnabled(false);
    }
}
