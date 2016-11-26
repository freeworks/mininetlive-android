package com.kouchen.mininetlive.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kouchen.mininetlive.R;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by cainli on 16/7/14.
 */
public class WithdrawAlertDialog extends Dialog {

    public WithdrawAlertDialog(Context context) {
        super(context, R.style.payDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_alert_layout);
        ButterKnife.bind(this);
        setCancelable(true);
    }

    @OnClick({R.id.knowBtn})
    public void onClickKnow() {
        dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dismiss();
    }
}
