package com.kouchen.mininetlive.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.ViewGroup;

import com.kouchen.mininetlive.MNLApplication;
import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.push.PushService;
import com.kouchen.mininetlive.ui.base.BaseActivity;
import com.kouchen.mininetlive.ui.base.BaseFragment;
import com.kouchen.mininetlive.ui.widget.BottomTab;
import com.kouchen.mininetlive.ui.widget.BottomTabGroup;

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

    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        long start = SystemClock.uptimeMillis();
        Log.e(TAG, "start:" + start);
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
                        currentIndex = 0;
                        switchContent(currentFragemnt, homeFragment);
                        break;
                    case R.id.tab_live:
                        currentIndex = 1;
                        switchContent(currentFragemnt, liveFragment);
                        break;
                    case R.id.tab_me:
                        currentIndex = 2;
                        if (!isLogin()) {
                            showLoginActivity();
                        }
                        switchContent(currentFragemnt, meFragment);
                        break;
                }
            }


        });
        homeTab.setChecked(true);

        toSplash();
        Log.e(TAG, "end:" + (SystemClock.uptimeMillis() - start));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int tabIndex = intent.getIntExtra("tabIndex", currentIndex);
        if (currentIndex != tabIndex) {
            switch (tabIndex) {
                case 0:
                    homeTab.setChecked(true);
                    break;
                case 1:
                    liveTab.setChecked(true);
                    break;
                case 2:
                    meTab.setChecked(true);
                    break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (currentFragemnt != null) {
            currentFragemnt.onResume();
        }
    }

    @Override
    protected void handleLogin(int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED) {
            currentIndex = 0;
            homeTab.setChecked(true);
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
