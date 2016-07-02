package com.kouchen.mininetlive.account;

import android.view.View;

import com.kouchen.mininetlive.AbsTitlebarActivity;
import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.auth.UserInfo;
import com.kouchen.mininetlive.ui.ItemView2;

import butterknife.BindView;

/**
 * Created by cainli on 16/6/24.
 */
public class EditGenderActivity extends AbsTitlebarActivity {

    @BindView(R.id.femaleItem)
    ItemView2 femaleItem;
    @BindView(R.id.maleItem)
    ItemView2 maleItem;

    @Override
    protected int getContentResId() {
        return R.layout.activity_me_edit_gender;
    }

    @Override
    public String getTitleString() {
        return "性别";
    }

    @Override
    protected void onResume() {
        super.onResume();
        UserInfo userInfo = getUserInfo();
        if (userInfo != null) {
            if (userInfo.getGender() == 0) {
                femaleItem.setChecked(true);
                maleItem.setChecked(false);
            } else {
                femaleItem.setChecked(true);
                maleItem.setChecked(false);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.femaleItem:
                femaleItem.setChecked(true);
                maleItem.setChecked(false);
                break;
            case R.id.maleItem:
                femaleItem.setChecked(false);
                maleItem.setChecked(true);
                break;
        }
    }
}
