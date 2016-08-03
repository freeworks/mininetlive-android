package com.kouchen.mininetlive.ui;

import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.models.RecordInfo;
import com.kouchen.mininetlive.ui.widget.FrontCoverImageView;
import com.kouchen.mininetlive.ui.widget.GlideRoundTransform;
import com.kouchen.mininetlive.utils.DisplayUtil;

/**
 * Created by cainli on 16/7/7.
 */
public class RecordViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.nickname)
    TextView nickname;
    @BindView(R.id.frontCover)
    FrontCoverImageView frontCover;
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
        frontCover.setUrl(t.getFrontCover());
        if (date != null) {
            date.setText("时间:"+t.getDate());
        }
    }
}