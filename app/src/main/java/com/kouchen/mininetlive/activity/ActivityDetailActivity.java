package com.kouchen.mininetlive.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.pay.PayActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cainli on 16/6/21.
 */
public class ActivityDetailActivity extends PayActivity {
    @BindView(R.id.frontCover)
    ImageView frontCover;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.datetime)
    TextView date;
    @BindView(R.id.onlineCount)
    TextView onlineCount;
    @BindView(R.id.nickname)
    TextView nickname;
    @BindView(R.id.avatar)
    ImageView avatar;
    @BindView(R.id.desc)
    TextView desc;

    ActivityInfo info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        info = (ActivityInfo) getIntent().getSerializableExtra("activityInfo");

        Glide.with(this)
                .load(info.getFrontCover())
                .centerCrop()
                .placeholder(R.drawable.img_default)
                .crossFade()
                .into(frontCover);
        title.setText(info.getTitle());
        onlineCount.setText("0");
        nickname.setText(info.getUid());
        Glide.with(this)
                .load(info.getOwner().getAvatar())
                .centerCrop()
                .placeholder(R.drawable.ic_launcher)
                .crossFade()
                .into(avatar);
        desc.setText(info.getDesc());
    }
}
