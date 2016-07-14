package com.kouchen.mininetlive.ui;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;
import com.kouchen.mininetlive.ui.base.AbsTitlebarActivity;
import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.models.UserInfo;
import com.kouchen.mininetlive.ui.widget.GlideCircleTransform;
import com.kouchen.mininetlive.ui.widget.ItemView2;

import butterknife.BindView;

/**
 * Created by cainli on 16/6/24.
 */
public class EditMeActivity extends AbsTitlebarActivity {

    @BindView(R.id.avatarItem)
    ItemView2 avatarItem;
    @BindView(R.id.nicknameItem)
    ItemView2 nickanmeItem;
    @BindView(R.id.genderItem)
    ItemView2 genderItem;
    @BindView(R.id.phoneItem)
    ItemView2 phoneItem;

    @Override
    protected void onResume() {
        super.onResume();
        UserInfo userInfo = getUserInfo();
        if(userInfo != null){
            Glide.with(this)
                    .load(userInfo.getAvatar())
                    .centerCrop()
                    .placeholder(R.drawable.ic_avatar_default)
                    .crossFade()
                    .transform(new GlideCircleTransform(this))
                    .into(avatarItem.getAvatarView());

            nickanmeItem.setValue(userInfo.getNickname());
            genderItem.setValue(userInfo.getGender() == 0?"女":"男");
            phoneItem.setValue(TextUtils.isEmpty(userInfo.getPhone())?"未绑定":userInfo.getPhone());
        }
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_me_edit;
    }

    @Override
    public String getTitleString() {
        return "个人信息";
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.avatarItem:
                break;
            case R.id.nicknameItem:
                intent = new Intent(this,EditNickActivity.class);
                startActivity(intent);
                break;
            case R.id.genderItem:
                intent = new Intent(this,EditGenderActivity.class);
                startActivity(intent);
                break;
            case R.id.phoneItem:
                break;
        }
    }
}
