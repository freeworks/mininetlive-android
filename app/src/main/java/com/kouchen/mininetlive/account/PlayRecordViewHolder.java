package com.kouchen.mininetlive.account;

import android.view.View;
import android.widget.TextView;

import com.kouchen.mininetlive.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cainli on 16/7/7.
 */
public class PlayRecordViewHolder extends RecordViewHolder {

    @BindView(R.id.playCount)
    TextView playCount;

    public PlayRecordViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(RecordInfo r) {
        super.setData(r);
        playCount.setText(String.valueOf(((PlayRecordInfo) r).getPlayCount()));
    }
}
