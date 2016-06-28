package com.kouchen.mininetlive.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.pay.PayActivity;
import com.kouchen.mininetlive.pay.PayChannel;
import com.kouchen.mininetlive.ui.TitlebarView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @BindView(R.id.button)
    TextView button;

    ActivityInfo info;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titlebarView.setTransparentBackground(true);
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

    @Override
    protected View getContentView() {
        return LayoutInflater.from(this).inflate(R.layout.activity_detail, null);
    }


    @OnClick({R.id.button})
    public void onClick(View view) {
        if(view.getId() == R.id.button){
            pay(PayChannel.CHANNEL_WECHAT,1);
        }
    }
}
