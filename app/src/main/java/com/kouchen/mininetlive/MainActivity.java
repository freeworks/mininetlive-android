package com.kouchen.mininetlive;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import com.kouchen.mininetlive.account.MeFragment;
import com.kouchen.mininetlive.activity.HomeFragment;
import com.kouchen.mininetlive.activity.LiveFragment;
import com.kouchen.mininetlive.auth.LoginActivity;
import com.kouchen.mininetlive.base.BaseActivity;
import com.kouchen.mininetlive.base.BaseFragment;
import com.kouchen.mininetlive.ui.BottomTab;
import com.kouchen.mininetlive.ui.BottomTabGroup;

import java.util.List;

/**
 * Created by cainli on 16/6/21.
 */
public class MainActivity extends BaseActivity {

    private ViewGroup container;
    private BottomTab homeTab, liveTab, meTab;

    private BaseFragment currentFragemnt;
    private HomeFragment homeFragment;
    private LiveFragment liveFragment;
    private MeFragment meFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container = (ViewGroup) findViewById(R.id.container);
        BottomTabGroup root = (BottomTabGroup) findViewById(R.id.bottom_bar_root);
        homeTab = (BottomTab) root.getChildAt(0);
        liveTab = (BottomTab) root.getChildAt(1);
        meTab = (BottomTab) root.getChildAt(2);
        homeFragment = new HomeFragment();
        liveFragment = new LiveFragment();
        meFragment = new MeFragment();
        root.setOnCheckedChangeListener(new BottomTabGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(BottomTabGroup root, int checkedId) {

                switch (checkedId) {
                    case R.id.tab_home:
                        switchContent(currentFragemnt, homeFragment);
                        break;
                    case R.id.tab_live:
                        switchContent(currentFragemnt, liveFragment);
                        break;
                    case R.id.tab_me:
                        if (!isLogin()) {
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivityForResult(intent, Constants.RequestCode.LOGIN);
                        }
                        switchContent(currentFragemnt, meFragment);
                        break;
                }
            }


        });
        homeTab.setChecked(true);

        toSplash();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (currentFragemnt != null) {
            currentFragemnt.onResume();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.RequestCode.LOGIN) {
            if (resultCode == RESULT_CANCELED) {
                homeTab.setChecked(true);
            }
        }
    }

    public void toSplash() {
        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);
    }


    public void switchContent(BaseFragment from, BaseFragment to) {
        if (currentFragemnt != to) {
            currentFragemnt = to;
//            FragmentTransaction transaction = mFragmentMan.beginTransaction().setCustomAnimations(
//                    android.R.anim.fade_in, R.anim.slide_out);
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction transaction = fm.beginTransaction();
            if (!to.isAdded()) {    // 先判断是否被add过
                if (from == null) {
                    to.show();
                    transaction.add(R.id.container, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
                } else {
                    from.hide();
                    to.show();
                    transaction.hide(from).add(R.id.container, to).commit(); // 隐藏当前的fragment，add下一个到Activity中
                }
            } else {
                currentFragemnt = to;
                if (from == null) {
                    to.show();
                    transaction.show(to).commit(); // 隐藏当前的fragment，显示下一个
                } else {
                    from.hide();
                    to.show();
                    transaction.hide(from).show(to).commit(); // 隐藏当前的fragment，显示下一个
                }

            }
            to.onResume();
        }
    }

    public void goHomeTab() {
        homeTab.setChecked(true);
    }
}
