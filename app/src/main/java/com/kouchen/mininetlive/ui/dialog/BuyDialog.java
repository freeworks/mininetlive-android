package com.kouchen.mininetlive.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kouchen.mininetlive.R;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by cainli on 16/7/14.
 */
public class BuyDialog extends Dialog {

    private static final String TAG = "BuyDialog";

    @BindView(R.id.amount)
    TextView amountTv;
    @BindView(R.id.alipay)
    TextView alipay;
    @BindView(R.id.wxpay)
    TextView wxpay;

    public BuyDialog(Context context) {
        super(context, R.style.payDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_buy_layout);
        ButterKnife.bind(this);
        setCancelable(true);
    }

    public void show(int amount, final View.OnClickListener alipayOnclickListener, final View.OnClickListener wxOnclickListener) {
        amountTv.setText(String.valueOf(amount));
        wxpay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                wxOnclickListener.onClick(view);
                dismiss();
            }
        });
        alipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alipayOnclickListener.onClick(view);
                dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dismiss();
    }
}
