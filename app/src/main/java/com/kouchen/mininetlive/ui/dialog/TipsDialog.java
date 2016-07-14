package com.kouchen.mininetlive.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.kouchen.mininetlive.R;


import java.util.concurrent.TimeUnit;

import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;


/**
 * Created by cainli on 16/7/14.
 */
public class TipsDialog extends Dialog {

    private static final String TAG = "TipsDialog";

    private static int icon1 = R.drawable.ic_buy;
    private static int icon2 = R.drawable.ic_reservation;
    private static int icon3 = R.drawable.ic_reward;

    private ImageView iconView;

    public TipsDialog(Context context) {
        super(context, R.style.tipsDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_tips);
        ButterKnife.bind(this);
        iconView = (ImageView) findViewById(R.id.icon);
        setCancelable(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        dismiss();
    }

    public void showReward() {
        iconView.setImageResource(R.drawable.ic_reward);
        show();
    }

    public void showBuy() {
        iconView.setImageResource(R.drawable.ic_buy);
        show();
    }

    public void showAppointment() {
        iconView.setImageResource(R.drawable.ic_reservation);
        show();
    }

    public void show() {
        super.show();
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("");
            }
        }).debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        hide();
                        Log.d(TAG, "show->hide");
                    }
                });
    }
}
