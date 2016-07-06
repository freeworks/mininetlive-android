package com.kouchen.mininetlive.account;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.kouchen.mininetlive.AboutActivity;
import com.kouchen.mininetlive.AbsTitlebarFragment;
import com.kouchen.mininetlive.MNLApplication;
import com.kouchen.mininetlive.MainActivity;
import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.auth.UserInfo;
import com.kouchen.mininetlive.ui.GlideCircleTransform;
import com.kouchen.mininetlive.ui.ItemView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by cainli on 16/6/24.
 */
public class MeFragment extends AbsTitlebarFragment {

    private static final String TAG = "MeFragment";

    @BindView(R.id.reward)
    ItemView reward;
    @BindView(R.id.appointmentRecord)
    ItemView appointmentRecord;
    @BindView(R.id.payRecord)
    ItemView payRecord;
    @BindView(R.id.playRecord)
    ItemView playRecord;
    @BindView(R.id.about)
    ItemView about;
    @BindView(R.id.logout)
    TextView logout;

    @BindView(R.id.avatar)
    ImageView avatar;
    @BindView(R.id.nickname)
    TextView nickName;
    @BindView(R.id.phone)
    TextView phone;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        UserInfo userInfo = getUserInfo();
        if (userInfo == null) {
            return;
        } else {
            setUserInfo(userInfo);
        }
    }

    private void setUserInfo(UserInfo userInfo) {
        if (userInfo == null) {
            avatar.setBackgroundResource(R.drawable.ic_avatar_default);
            nickName.setText("未知");
            phone.setText("");
        } else {
            Glide.with(this)
                    .load(userInfo.getAvatar())
                    .centerCrop()
                    .placeholder(R.drawable.ic_avatar_default)
                    .crossFade()
                    .transform(new GlideCircleTransform(getContext()))
                    .into(avatar);

            nickName.setText(userInfo.getNickname());
            phone.setText(userInfo.getPhone());
        }
    }

    @Override
    protected void initView(View view) {
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.fragment_me;
    }

    @Override
    protected String getTitle() {
        return "我的";
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @OnClick(R.id.logout)
    public void logout() {
        MNLApplication.getCacheManager().unset("user");
        setUserInfo(null);
        EMClient.getInstance().logout(true, new EMCallBack() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "onSuccess: 退出成功");
            }

            @Override
            public void onProgress(int progress, String status) {
            }

            @Override
            public void onError(int code, String message) {
                Log.e(TAG, "onError: 退出失败", new Exception("code " + message));
            }
        });
        ((MainActivity) getActivity()).goHomeTab();
    }

    @OnClick({R.id.avatar, R.id.reward, R.id.appointmentRecord, R.id.payRecord,
            R.id.playRecord, R.id.about})
    public void onClick(View view) {
        Intent intent = null;
        switch (view.getId()) {
            case R.id.avatar:
                intent = new Intent(getContext(), EditMeActivity.class);
                break;
            case R.id.reward:
                intent = new Intent(getContext(), BalanceActivity.class);
                break;
            case R.id.appointmentRecord:
                intent = new Intent(getContext(), AppointmentRecordActivity.class);
                break;
            case R.id.payRecord:
                intent = new Intent(getContext(), PayRecordActivity.class);
                break;
            case R.id.playRecord:
                intent = new Intent(getContext(), PlayRecordActivity.class);
                break;
            case R.id.about:
                intent = new Intent(getContext(), AboutActivity.class);
                break;
        }
        if (intent == null) {
            return;
        }
        startActivity(intent);
    }
}
