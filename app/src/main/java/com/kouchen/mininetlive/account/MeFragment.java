package com.kouchen.mininetlive.account;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.kouchen.mininetlive.AbsTitlebarFragment;
import com.kouchen.mininetlive.MainActivity;
import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.ui.ItemView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cainli on 16/6/24.
 */
public class MeFragment extends AbsTitlebarFragment{

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
        SharedPreferences sp = getActivity().getSharedPreferences("account", 0);
        sp.edit().clear().apply();
        ( (MainActivity)getActivity()).goHomeTab();
    }

    @OnClick({R.id.reward, R.id.appointmentRecord, R.id.payRecord,
            R.id.playRecord, R.id.about})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reward:
                break;
            case R.id.appointmentRecord:
                break;
            case R.id.payRecord:
                break;
            case R.id.playRecord:
                break;
            case R.id.about:
                break;
        }
    }
}
