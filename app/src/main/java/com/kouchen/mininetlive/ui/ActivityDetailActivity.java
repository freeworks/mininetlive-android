package com.kouchen.mininetlive.ui;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.OnClick;
import com.bumptech.glide.Glide;
import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.presenter.PayPresenter;
import com.kouchen.mininetlive.models.ActivityInfo;
import com.kouchen.mininetlive.ui.widget.FullActiivty;
import com.kouchen.mininetlive.ui.widget.GlideCircleTransform;
import com.kouchen.mininetlive.ui.widget.VideoPlayer;
import javax.inject.Inject;

/**
 * Created by cainli on 16/6/21.
 */
public class ActivityDetailActivity extends PayActivity {
    private static final String TAG = "ActivityDetailActivity";
    @BindView(R.id.atitle)
    TextView title;
    @BindView(R.id.datetime)
    TextView date;
    @BindView(R.id.nickname)
    TextView nickname;
    @BindView(R.id.avatar)
    ImageView avatar;
    @BindView(R.id.desc)
    TextView desc;
    @BindView(R.id.button)
    TextView button;
    @BindView(R.id.appointmentCountLayout)
    View appointCountLayout;
    @BindView(R.id.appointmentCount)
    TextView appointmentCount;
    @BindView(R.id.share)
    TextView share;
    @BindView(R.id.onlineCount1)
    TextView onlineCount1;
    @BindView(R.id.playCount)
    TextView playCount;
    @BindView(R.id.labelOnlineUser)
    TextView onlineCount2;
    @BindView(R.id.onlineUserListLayout)
    View onlineUserListLayout;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.player)
    VideoPlayer player;

    private ActivityInfo info;

    @Inject
    PayPresenter payPresenter;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        titlebarView.setTransparentBackground(true);
        titlebarView.setVisibility(View.GONE);
//        int screenWidth = MNLApplication.getApplication().getScreenWidth();
//        player.getLayoutParams().height = (int) (screenWidth * 9 / 16f);
        info = (ActivityInfo) getIntent().getSerializableExtra("activityInfo");

        final String mVideoPath = info.isLiveStream() ? info.getLivePullPath() : info.getVideoPath();
        player.setup(mVideoPath,null,info.isLiveStream(),false);
        player.setFullScreenListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FullActiivty.startActivityFromNormal(ActivityDetailActivity.this,
                        mVideoPath,info.getId(), info.isLiveStream(),player.isPlaying(),player.getCurrentPosition());
            }
        });
        player.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Glide.with(this)
                .load(info.getFrontCover())
                .centerCrop()
                .placeholder(R.drawable.img_default)
                .crossFade()
                .into(player.getCover());
        title.setText(info.getTitle());
        nickname.setText(info.getOwner().getNickname());
        date.setText("时间：" + info.getDate());
        Glide.with(this)
                .load(info.getOwner().getAvatar())
                .centerCrop()
                .placeholder(R.drawable.ic_avatar_default)
                .crossFade()
                .transform(new GlideCircleTransform(this))
                .into(avatar);

        titlebarView.setBackLister(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        }, false);

        desc.setText(info.getDesc());

        playCount.setVisibility(View.GONE);
        appointCountLayout.setVisibility(View.GONE);
        onlineUserListLayout.setVisibility(View.GONE);
        onlineCount1.setVisibility(View.GONE);
        price.setVisibility(View.GONE);
        price.setText("￥" + info.getPrice());
        if (info.getStreamType() == 1) {
            switch (info.getActivityState()) {
                case 0:
                    appointCountLayout.setVisibility(View.VISIBLE);
                    appointmentCount.setText(String.valueOf(info.getAppointmentCount()));
                    if (info.getAppointmentState() == 0) {
                        button.setBackgroundResource(R.drawable.blue_rect_selector);
                        button.setText("立即预约");
                    } else {
                        button.setBackgroundResource(R.drawable.grey_disable);
                        button.setText("已经预约");
                    }
                    break;
                case 1:
                    onlineUserListLayout.setVisibility(View.VISIBLE);
                    onlineCount1.setVisibility(View.VISIBLE);
                    onlineCount1.setText(info.getOnlineCount() + "人在线观看");
                    onlineCount2.setText("在线人数:10002");
                    if (info.getActivityType() == 0) { //免费
                        price.setVisibility(View.VISIBLE);
                        button.setBackgroundResource(R.drawable.red_rect_selector);
                        button.setText("打赏红包");
                    } else {
                        price.setVisibility(View.VISIBLE);
                        if (info.getPayState() == 0) {
                            button.setBackgroundResource(R.drawable.green_rect_selector);
                            button.setText("购买");
                        } else {
                            button.setBackgroundResource(R.drawable.red_rect_selector);
                            button.setText("打赏红包");
                        }
                    }
                    break;
                case 2:
                    if (info.getActivityType() == 0) {
                        price.setVisibility(View.INVISIBLE);
                    } else {
                        price.setVisibility(View.VISIBLE);
                    }
                    button.setBackgroundResource(R.drawable.red_rect_selector);
                    button.setText("打赏红包");
                    break;

            }
        } else { //点播
            playCount.setVisibility(View.VISIBLE);
            playCount.setText("播放：" + info.getPlayCount() + "次");
            if (info.getActivityType() == 0) {
                button.setBackgroundResource(R.drawable.red_rect_selector);
                button.setText("打赏红包");
            } else {
                if (info.getPayState() == 0) {
                    button.setBackgroundResource(R.drawable.green_rect_selector);
                    button.setText("购买");
                } else {
                    button.setBackgroundResource(R.drawable.red_rect_selector);
                    button.setText("打赏红包");
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        player.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.stopPlayback();
    }

    @Override
    protected boolean isBelowTitleBar() {
        return false;
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_detail;
    }

    @Override
    public String getTitleString() {
        return "";
    }


    @OnClick({R.id.button})
    public void onClick(View view) {
        if (view.getId() == R.id.button) {
            if (info.getStreamType() == 0) {
                switch (info.getActivityState()) {
                    case 0:
                        appointment(info);
                        break;
                    case 1:
                        if (info.getActivityType() == 0) { //免费
                            reward();
                        } else {
                            if (info.getPayState() == 0) {
                                buy();
                            } else {
                                reward();
                            }
                        }
                        break;
                    case 2:
                        reward();
                        break;

                }
            } else { //点播
                if (info.getActivityType() == 0) {
                    reward();
                } else {
                    if (info.getPayState() == 0) {
                        buy();
                    } else {
                        reward();
                    }
                }
            }

        }
    }

    private void appointment(ActivityInfo info) {
        //activityPresenter.appointment(info.getId());
    }

    private void buy() {
        payPresenter.pay(info.getId(), PayChannel.CHANNEL_WECHAT, info.getPrice());

    }

    private void reward() {
        payPresenter.pay(info.getId(), PayChannel.CHANNEL_WECHAT, info.getPrice());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == VideoPlayer.FULLSCREEN_REQUESTCODE && resultCode == RESULT_OK){
            boolean isPlaying = data.getBooleanExtra("isPlaying", false);
            long currentPosition = data.getLongExtra("currentPosition", 0L);
            if(isPlaying){
                player.start();
            }else{
                player.pause();
            }
            if(!info.isLiveStream()){
                player.setCurrentPosition(currentPosition);
            }
        }
    }
}
