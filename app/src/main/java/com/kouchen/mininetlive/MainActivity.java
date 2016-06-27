package com.kouchen.mininetlive;

import android.content.Intent;
import android.os.Bundle;
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

/**
 * Created by cainli on 16/6/21.
 */
public class MainActivity extends BaseActivity {

    private ViewGroup container;
    private BottomTab homeTab, liveTab, meTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container = (ViewGroup) findViewById(R.id.container);
        BottomTabGroup root = (BottomTabGroup) findViewById(R.id.bottom_bar_root);
        homeTab = (BottomTab) root.getChildAt(0);
        liveTab = (BottomTab) root.getChildAt(1);
        meTab = (BottomTab) root.getChildAt(2);
        root.setOnCheckedChangeListener(new BottomTabGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(BottomTabGroup root, int checkedId) {

                switch (checkedId) {
                    case R.id.tab_home:
                        setFragment(new HomeFragment());
                        break;
                    case R.id.tab_live:
                        setFragment(new LiveFragment());
                        break;
                    case R.id.tab_me:
                        if (!isLogin()) {
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            startActivityForResult(intent, Constants.RequestCode.LOGIN);
                        }
                        setFragment(new MeFragment());
                        break;
                }
            }


        });
        homeTab.setChecked(true);

        toSplash();
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

    public void setFragment(BaseFragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

    public void goHomeTab() {
        homeTab.setChecked(true);
    }
}
