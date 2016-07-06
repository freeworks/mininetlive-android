package com.kouchen.mininetlive.account;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kouchen.mininetlive.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cainli on 16/7/7.
 */
public class RecordViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.nickname)
    TextView nickname;

    @BindView(R.id.date)
    @Nullable
    TextView date;

    public RecordViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void setData(RecordInfo t) {
        title.setText(t.getTitle());
        nickname.setText(t.getNickname());
        if (date != null) {
            date.setText(t.getDate());
        }
    }
}