package com.kouchen.mininetlive.account;

import android.view.View;

import com.kouchen.mininetlive.R;

import java.util.List;


/**
 * Created by cainli on 16/7/2.
 */
public class PaidRecordActivity extends RecordActivity {
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
        return new PaidRecordAdapter();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void success(Object list) {
        if (adapter!=null) {
            adapter.setData((List<? extends RecordInfo>) list);
        }
    }

    public static class PaidRecordAdapter extends RecordAdapter<PayRecordInfo> {

        @Override
        protected RecordViewHolder createViewHolder(View view) {
            return new PaidRecordViewHolder(view);
        }

        @Override
        protected int getItemLayoutResId() {
            return R.layout.item_paid_layout;
        }

        static class PaidRecordViewHolder extends RecordViewHolder<PayRecordInfo> {
            //TODO
            public PaidRecordViewHolder(View itemView) {
                super(itemView);
            }

            @Override
            public void setData(PayRecordInfo recordInfo) {
                super.setData(recordInfo);
                //TODO
            }
        }
    }
}
