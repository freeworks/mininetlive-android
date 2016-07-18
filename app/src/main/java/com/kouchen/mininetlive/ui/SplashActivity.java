package com.kouchen.mininetlive.ui;

import android.os.Bundle;
import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.ui.base.BaseActivity;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                finish();
            }
        }, 2000);
    }
}