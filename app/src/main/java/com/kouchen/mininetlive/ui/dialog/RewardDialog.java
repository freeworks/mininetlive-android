package com.kouchen.mininetlive.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kouchen.mininetlive.R;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


/**
 * Created by cainli on 16/7/14.
 */
public class RewardDialog extends Dialog {

    private static final String TAG = "RewardDialog";

    @BindView(R.id.alipay)
    TextView alipay;
    @BindView(R.id.wxpay)
    TextView wxpay;

    public RewardDialog(Context context) {
        super(context, R.style.payDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_pay_reward_layout);
        ButterKnife.bind(this);
        setCancelable(true);
    }

    public void show(View.OnClickListener alipayOnclickListener, View.OnClickListener wxOnclickListener) {
        wxpay.setOnClickListener(wxOnclickListener);
        alipay.setOnClickListener(alipayOnclickListener);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dismiss();
    }
}
