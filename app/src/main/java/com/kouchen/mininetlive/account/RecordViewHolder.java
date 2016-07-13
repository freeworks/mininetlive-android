package com.kouchen.mininetlive.account;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.ui.GlideCircleTransform;

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
    @BindView(R.id.frontCover)
    ImageView frontCover;
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
        Glide.with(itemView.getContext())
                .load(t.getFrontCover())
                .placeholder(R.drawable.img_default)
                .into(frontCover);
        if (date != null) {
            date.setText("时间:"+t.getDate());
        }
    }
}