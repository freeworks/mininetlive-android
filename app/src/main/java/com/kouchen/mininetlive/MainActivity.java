package com.kouchen.mininetlive;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kouchen.mininetlive.base.BaseActivity;
import com.kouchen.mininetlive.base.BaseFragment;
import com.kouchen.mininetlive.ui.BottomTab;
import com.kouchen.mininetlive.ui.BottomTabGroup;

/**
 * Created by cainli on 16/6/21.
 */
public class MainActivity extends BaseActivity {

    private ViewGroup container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        container = (ViewGroup) findViewById(R.id.container);
        BottomTabGroup root = (BottomTabGroup) findViewById(R.id.bottom_bar_root);
        BottomTab homeTab = (BottomTab) root.getChildAt(0);
        BottomTab liveTab = (BottomTab) root.getChildAt(1);
        BottomTab meTab = (BottomTab) root.getChildAt(2);
        homeTab.setChecked(true);
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
                        setFragment(new MeFragment());
                        break;
                }
            }
        });
    }

    public void setFragment(BaseFragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }
}
