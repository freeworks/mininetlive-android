package com.kouchen.mininetlive.activity;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.pay.PayActivity;

/**
 * Created by cainli on 16/6/21.
 */
public class ActivityDetailActivity extends PayActivity{

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_detail);
    }
}
