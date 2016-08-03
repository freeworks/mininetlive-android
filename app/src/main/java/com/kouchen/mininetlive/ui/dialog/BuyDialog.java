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
    private int amount;
    private View.OnClickListener alipayOnclickListener;
    private View.OnClickListener wxOnclickListener;


    public BuyDialog(Context context) {
        super(context, R.style.payDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_buy_layout);
        ButterKnife.bind(this);
        setCancelable(true);
        setData(amount, alipayOnclickListener, wxOnclickListener);
    }

    public void show(int amount, final View.OnClickListener alipayOnclickListener, final View.OnClickListener wxOnclickListener) {
        this.amount = amount;
        this.alipayOnclickListener = alipayOnclickListener;
        this.wxOnclickListener = wxOnclickListener;
        setData(amount, alipayOnclickListener, wxOnclickListener);
        show();
    }

    public void show(int amount, final View.OnClickListener payOnclickListener) {
        this.amount = amount;
        this.alipayOnclickListener = payOnclickListener;
        this.wxOnclickListener = payOnclickListener;
        setData(amount, alipayOnclickListener, wxOnclickListener);
        show();
    }

    private void setData(int amount, final View.OnClickListener alipayOnclickListener, final View.OnClickListener wxOnclickListener) {
        if(amountTv!= null){
            DecimalFormat myformat = new DecimalFormat();
            myformat.applyPattern("##,##0.00");
            amountTv.setText(myformat.format(amount/100f));
        }
        if (wxpay!=null){
            wxpay.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    wxOnclickListener.onClick(view);
                    dismiss();
                }
            });
        }
        if(alipay!=null){
            alipay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    alipayOnclickListener.onClick(view);
                    dismiss();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dismiss();
    }
}
