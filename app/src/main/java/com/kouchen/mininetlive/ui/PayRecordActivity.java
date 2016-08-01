package com.kouchen.mininetlive.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import android.widget.TextView;
import butterknife.BindView;
import com.kouchen.mininetlive.MNLApplication;
import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.di.components.DaggerAccountComponent;
import com.kouchen.mininetlive.di.modules.AccountModule;
import com.kouchen.mininetlive.presenter.AccountPresenter;
import com.kouchen.mininetlive.models.PayRecordInfo;
import com.kouchen.mininetlive.models.RecordInfo;
import javax.inject.Inject;

/**
 * Created by cainli on 16/7/2.
 */
public class PayRecordActivity extends RecordActivity {

    @Inject
    AccountPresenter presenter;

    @Override
    protected void initInject() {
        DaggerAccountComponent.builder()
                .accountModule(new AccountModule(this))
                .netComponent(((MNLApplication) getApplication()).getNetComponent())
                .build()
                .inject(this);
    }

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
            return new PayRecordViewHolder(view);
        }

        @Override
        protected int getItemLayoutResId() {
            return R.layout.item_pay_layout;
        }
    }

    public static class PayRecordViewHolder extends RecordViewHolder {

        @BindView(R.id.channel)
        TextView channel;

        @BindView(R.id.amount)
        TextView amount;

        @BindView(R.id.createDate)
        TextView createTime;

        @BindView(R.id.state)
        TextView state;

        public PayRecordViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(RecordInfo r) {
            super.setData(r);
            amount.setText(String.valueOf(((PayRecordInfo) r).getAmount()));
            channel.setText(((PayRecordInfo)r).getChannelString());
            createTime.setText(r.getCreateTime());
            state.setText(((PayRecordInfo)r).getStateString());
        }
    }
}
