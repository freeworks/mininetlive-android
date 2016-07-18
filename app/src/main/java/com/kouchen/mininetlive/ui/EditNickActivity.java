package com.kouchen.mininetlive.ui;

import android.view.View;
import android.widget.EditText;

import com.kouchen.mininetlive.ui.base.AbsTitlebarActivity;
import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.models.UserInfo;

import butterknife.BindView;

/**
 * Created by cainli on 16/6/24.
 */
public class EditNickActivity extends AbsTitlebarActivity{

    @BindView(R.id.nickname)
    EditText nickname;

    @Override
    protected void initView(View contentView) {

    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_me_edit_nickname;
    }

    @Override
    public String getTitleString() {
        return "昵称";
    }

    @Override
    protected void onResume() {
        super.onResume();
        titlebarView.setRightTextView("保存",this);
        UserInfo userInfo = getUserInfo();
        if (userInfo != null) {
            nickname.setText(userInfo.getNickname());
        }
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.right){
            //todo save
        }else if(view.getId() == R.id.clear){
            nickname.setText("");
        }
    }
}
