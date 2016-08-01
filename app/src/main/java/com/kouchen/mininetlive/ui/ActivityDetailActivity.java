package com.kouchen.mininetlive.ui;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.kouchen.mininetlive.MNLApplication;
import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.contracts.ActivityDetailContract;
import com.kouchen.mininetlive.di.components.DaggerActivityDetailComponent;
import com.kouchen.mininetlive.di.modules.ActivityDetailModule;
import com.kouchen.mininetlive.models.ActivityInfo;
import com.kouchen.mininetlive.models.OnlinUserInfo;
import com.kouchen.mininetlive.presenter.ActivityDetailPresenter;
import com.kouchen.mininetlive.ui.base.AbsTitlebarActivity;
import com.kouchen.mininetlive.ui.dialog.BuyDialog;
import com.kouchen.mininetlive.ui.dialog.RewardDialog;
import com.kouchen.mininetlive.ui.dialog.ShareDialog;
import com.kouchen.mininetlive.ui.widget.FullActiivty;
import com.kouchen.mininetlive.ui.widget.GlideCircleTransform;
import com.kouchen.mininetlive.ui.widget.VideoPlayer;
import com.pingplusplus.android.Pingpp;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by cainli on 16/6/21.
 */
public class ActivityDetailActivity extends AbsTitlebarActivity
        implements ActivityDetailContract.View {
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
    @BindView(R.id.onlineUserListLayout)
    View onlineUserListLayout;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.player)
    VideoPlayer player;
    @BindView(R.id.pricelayout)
    RelativeLayout pricelayout;
    @BindView(R.id.labelOnlineUser)
    TextView labelOnlineUser;
    @BindView(R.id.onlineUserList)
    RecyclerView onlineUserList;

    OnlineUserAdapter adapter;

    ShareDialog shareDialog;
    BuyDialog buyDialog;
    RewardDialog rewardDialog;

    Subscription intervalSubscribe;

    private ActivityInfo info;

    @Inject
    ActivityDetailPresenter presenter;

    @Override
    protected void initInject() {
        DaggerActivityDetailComponent.builder()
                .netComponent(((MNLApplication) getApplication()).getNetComponent())
                .activityDetailModule(new ActivityDetailModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void initView(View contentView) {
        titlebarView.setTransparentBackground(true);
        titlebarView.setVisibility(View.GONE);
        //        int screenWidth = MNLApplication.getApplication().getScreenWidth();
        //        player.getLayoutParams().height = (int) (screenWidth * 9 / 16f);
        info = (ActivityInfo) getIntent().getSerializableExtra("activityInfo" );

        final String mVideoPath = info.isLiveStream() ? info.getLivePullPath() : info.getVideoPath();
        player.setup(mVideoPath, null, info.isLiveStream(), false,canplay());
        player.setFullScreenListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FullActiivty.startActivityFromNormal(ActivityDetailActivity.this, mVideoPath,
                        info.getId(), info.isLiveStream(), player.isPlaying(),
                        player.getCurrentPosition());
            }
        });
        player.setOnBackListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        player.setCover(info.getFrontCover());

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

        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        onlineUserList.setLayoutManager(linearLayoutManager);
        //设置适配器
        adapter = new OnlineUserAdapter();
        onlineUserList.setAdapter(adapter);

        pricelayout.setVisibility(View.GONE);
        price.setText(info.getPriceStr());
        if (info.isLiveStream()) {
            switch (info.getActivityState()) {
                case 0:
                    appointCountLayout.setVisibility(View.VISIBLE);
                    appointmentCount.setText(info.getAppointmentCount());
                    if (info.getAppointmentState() == 0) {
                        button.setBackgroundResource(R.drawable.blue_rect_selector);
                        button.setText("立即预约" );
                        button.setTag("appointment" );
                    } else {
                        button.setBackgroundResource(R.drawable.grey_disable);
                        button.setText("已经预约" );
                        button.setTag("appointmented" );
                    }
                    break;
                case 1:
                    onlineUserListLayout.setVisibility(View.VISIBLE);
                    onlineCount1.setVisibility(View.VISIBLE);
                    onlineCount1.setText(info.getOnlineCount() + "人在线观看" );
                    labelOnlineUser.setText("在线人数:" + info.getOnlineCount());
                    if (info.isFree()) { //免费
                        pricelayout.setVisibility(View.VISIBLE);
                        button.setBackgroundResource(R.drawable.red_rect_selector);
                        button.setText("打赏红包" );
                        button.setTag("reward" );
                    } else {
                        pricelayout.setVisibility(View.VISIBLE);
                        if (info.getPayState() == 0) {
                            button.setBackgroundResource(R.drawable.green_rect_selector);
                            button.setText("购买" );
                            button.setTag("buy" );
                        } else {
                            button.setBackgroundResource(R.drawable.red_rect_selector);
                            button.setText("打赏红包" );
                            button.setTag("reward" );
                        }
                    }
                    break;
                case 2:
                    if (info.isFree()) {
                        pricelayout.setVisibility(View.INVISIBLE);
                    } else {
                        pricelayout.setVisibility(View.VISIBLE);
                    }
                    button.setBackgroundResource(R.drawable.grey_rect_selector);
                    button.setText("已结束" );
                    button.setEnabled(false);
                    break;
            }
        } else { //点播
            playCount.setVisibility(View.VISIBLE);
            playCount.setText("播放：" + info.getPlayCount() + "次" );
            if (info.isFree()) {
                button.setBackgroundResource(R.drawable.red_rect_selector);
                button.setText("打赏红包" );
                button.setTag("reward" );
            } else {
                pricelayout.setVisibility(View.VISIBLE);
                if (info.getPayState() == 0) {
                    button.setBackgroundResource(R.drawable.green_rect_selector);
                    button.setText("购买" );
                    button.setTag("buy" );
                } else {
                    button.setBackgroundResource(R.drawable.red_rect_selector);
                    button.setText("打赏红包" );
                    button.setTag("reward" );
                }
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (info.isLiveStream() && info.isLiving()) {
            presenter.join(info.getId());
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (info.isLiveStream() && info.isLiving()) {
            presenter.leave(info.getId());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(canplay()){
            player.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (info.isLiveStream() && info.isLiving()) {
            presenter.getOnlineMemberList(info.getId());
            intervalSubscribe = Observable.interval(2, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            presenter.getOnlineMemberList(info.getId());
                        }
                    });
        }

        if(canplay()){
            player.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(intervalSubscribe!=null){
            intervalSubscribe.unsubscribe();
        }
        if(canplay()){
            player.stopPlayback();
        }
    }

    private boolean canplay(){
        if((info.isLiveStream() && info.isLiving()) || !info.isLiveStream()){
            return true;
        }
        return false;
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
            switch (view.getTag().toString()) {
                case "appointment":
                    if (!isLogin()) {
                        showLoginActivity();
                        return;
                    }
                    showProgressView("预约中...");
                    presenter.appointment(info.getId());
                    break;
                case "buy":
                    if (buyDialog == null) {
                        buyDialog = new BuyDialog(this);
                    }
                    if (!buyDialog.isShowing()) {
                        buyDialog.show(info.getPrice(), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (!isLogin()) {
                                    showLoginActivity();
                                    return;
                                }
                                PayChannel channel;
                                if (view.getId() == R.id.wxpay) {
                                    channel = PayChannel.CHANNEL_WECHAT;
                                } else {
                                    channel = PayChannel.CHANNEL_ALIPAY;
                                }
                                buyDialog.dismiss();
                                showProgressView("获取支付信息..." );
                                presenter.pay(info.getId(), channel, info.getPrice(), 1);
                            }
                        });
                    }
                    break;
                case "reward":
                    if (rewardDialog == null) {
                        rewardDialog = new RewardDialog(this);
                    }
                    if (!rewardDialog.isShowing()) {
                        rewardDialog.show(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (!isLogin()) {
                                    showLoginActivity();
                                    return;
                                }
                                PayChannel channel;
                                if (view.getId() == R.id.wxpay) {
                                    channel = PayChannel.CHANNEL_WECHAT;
                                } else {
                                    channel = PayChannel.CHANNEL_ALIPAY;
                                }
                                rewardDialog.dismiss();
                                showProgressView("获取支付信息..." );
                                presenter.pay(info.getId(), channel, rewardDialog.getAmount(), 0);
                            }
                        });
                    }
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //支付页面返回处理
        if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
            hideProgressView();
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result" );
                if (result == null) {
                    Toast.makeText(this, "支付失败!", Toast.LENGTH_SHORT).show();
                    return;
                }
                switch (result) {
                    case "success":
                        Toast.makeText(this, "支付成功!", Toast.LENGTH_SHORT).show();
                        break;
                    case "fail":
                        Toast.makeText(this, "支付失败，请重新支付!", Toast.LENGTH_SHORT).show();
                        break;
                    case "cancel":
                        //                        msg = "取消支付成功!";
                        break;
                    case "invalid":
                        Toast.makeText(this, "请检查是安装微信/支付宝客户端!", Toast.LENGTH_SHORT).show();
                        break;
                }
                // 处理返回值
                // "success" - 支付成功
                // "fail"    - 支付失败
                // "cancel"  - 取消支付
                // "invalid" - 支付插件未安装（一般是微信客户端未安装的情况）
                String errorMsg = data.getExtras().getString("error_msg" ); // 错误信息
                String extraMsg = data.getExtras().getString("extra_msg" ); // 错误信息
                Log.i(TAG, "errorMsg:" + errorMsg);
                Log.i(TAG, "extraMsg:" + extraMsg);
            }
        } else if (requestCode == VideoPlayer.FULLSCREEN_REQUESTCODE && resultCode == RESULT_OK) {
            boolean isPlaying = data.getBooleanExtra("isPlaying", false);
            long currentPosition = data.getLongExtra("currentPosition", 0L);
            if (isPlaying) {
                player.start();
            } else {
                player.pause();
            }
            if (!info.isLiveStream()) {
                player.setCurrentPosition(currentPosition);
            }
        }
    }

    @OnClick(R.id.share)
    protected void share() {
        if (shareDialog == null) {
            shareDialog = new ShareDialog(this);
        }
        if (shareDialog.isShowing()) {
            return;
        }
        shareDialog.show(info.getId(), info.getTitle(), info.getDesc(), info.getFrontCover());
    }

    @Override
    public void onBackPressed() {
        if (panelView.isShowing()) {
            panelView.hide();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void showProgress() {
        showProgressView("正在获取订单..." );
    }

    @Override
    public void hideProgress() {
        hideProgressView();
    }

    @Override
    public void onError(String msg) {
        hideProgress();
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(Object data) {
        hideProgress();
        Toast.makeText(this,(String)data,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetChargeSuccess(String charge) {
        showProgressView("正在支付..." );
        Pingpp.createPayment(this, charge);
    }

    @Override
    public void onGetMemberListSuccess(List<OnlinUserInfo> onlinUserInfos) {
        int size;
        if(onlinUserInfos == null){
            size = 0;
        }else{
            size = onlinUserInfos.size();
        }
        onlineCount1.setText(size + "人在线观看" );
        labelOnlineUser.setText("在线人数:" +size);
        adapter.setData(onlinUserInfos);
    }

    public class OnlineUserAdapter extends RecyclerView.Adapter<OnlinUserViewHolder> {

        private List<OnlinUserInfo> onlinUserInfos;

        @Override
        public OnlinUserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.online_user_item, parent, false);
            return new OnlinUserViewHolder(view);
        }

        @Override
        public void onBindViewHolder(OnlinUserViewHolder holder, int position) {
            if (holder == null) {
                return;
            }
            holder.setData(onlinUserInfos.get(position));
        }

        @Override
        public int getItemCount() {
            if (onlinUserInfos == null) {
                return 0;
            }
            return onlinUserInfos.size();
        }

        public void setData(List<OnlinUserInfo> onlinUserInfos) {
            this.onlinUserInfos = onlinUserInfos;
            notifyDataSetChanged();
        }

    }

    static class OnlinUserViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.avatar)
        ImageView avatar;
        @BindView(R.id.nickname)
        TextView nickname;

        public OnlinUserViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        public void setData(OnlinUserInfo info) {
            nickname.setText(info.getNickname());
            Glide.with(itemView.getContext())
                    .load(info.getAvatar())
                    .centerCrop()
                    .placeholder(R.drawable.ic_avatar_default)
                    .crossFade()
                    .transform(new GlideCircleTransform(itemView.getContext()))
                    .into(avatar);
        }
    }

}
