package com.kouchen.mininetlive;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by cainli on 16/6/26.
 */
public class BalanceActivity extends AbsTitlebarActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titlebarView.setRightTextView("提现明细", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BalanceActivity.this, BalanceDetailListActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_balance;
    }

    @Override
    public String getTitleString() {
        return "我的分红";
    }
}
