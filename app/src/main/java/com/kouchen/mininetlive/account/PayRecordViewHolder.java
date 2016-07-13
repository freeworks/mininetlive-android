package com.kouchen.mininetlive.account;

import android.view.View;
import android.widget.TextView;

import com.kouchen.mininetlive.R;

import butterknife.BindView;

/**
 * Created by cainli on 16/7/7.
 */
public class PayRecordViewHolder extends RecordViewHolder {

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
