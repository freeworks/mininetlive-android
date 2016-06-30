package com.kouchen.mininetlive;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

/**
 * Created by cainli on 16/6/26.
 */
public class BalanceDetailListActivity extends AbsTitlebarActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_balance_detaillist;
    }

    @Override
    public String getTitleString() {
        return "提现明细";
    }
}
