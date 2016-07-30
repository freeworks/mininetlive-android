package com.kouchen.mininetlive.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.ui.widget.RewardView;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by cainli on 16/7/14.
 */
public class RewardDialog extends Dialog {

    private static final String TAG = "RewardDialog";

    @BindView(R.id.alipay)
    TextView alipay;
    @BindView(R.id.wxpay)
    TextView wxpay;
    @BindView(R.id.amountLayout)
    RewardView rewardView;

    private View.OnClickListener alipayOnclickListener;
    private View.OnClickListener wxOnclickListener;

    public RewardDialog(Context context) {
        super(context, R.style.payDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_pay_reward_layout);
        ButterKnife.bind(this);
        setCancelable(true);
        wxpay.setOnClickListener(wxOnclickListener);
        alipay.setOnClickListener(alipayOnclickListener);
    }

    public void show(View.OnClickListener alipayOnclickListener, View.OnClickListener wxOnclickListener) {
        this.alipayOnclickListener = alipayOnclickListener;
        this.wxOnclickListener = wxOnclickListener;
        show();
    }

    public void show(View.OnClickListener payOnclickListener) {
        this.alipayOnclickListener = payOnclickListener;
        this.wxOnclickListener = payOnclickListener;
        show();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dismiss();
    }

    public int getAmount() {
        return rewardView.getAmount();
    }
}
