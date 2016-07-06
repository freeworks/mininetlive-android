package com.kouchen.mininetlive.account;

import android.view.View;

import com.kouchen.mininetlive.R;


/**
 * Created by cainli on 16/7/2.
 */
public class PayRecordActivity extends RecordActivity {
    @Override
    public String getTitleString() {
        return "购买记录";
    }

    @Override
    protected void loadData() {
        presenter.getPayRecordList();
    }

    @Override
    protected RecordAdapter getAdapter() {
        return new PayRecordAdapter();
    }

    public static class PayRecordAdapter extends RecordAdapter<PayRecordInfo> {
        @Override
        protected RecordViewHolder createViewHolder(View view) {
            return new RecordViewHolder(view);
        }

        @Override
        protected int getItemLayoutResId() {
            return R.layout.item_pay_layout;
        }
    }
}
