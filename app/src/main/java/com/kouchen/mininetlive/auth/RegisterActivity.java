package com.kouchen.mininetlive.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.base.BaseActivity;

/**
 * Created by cainli on 16/6/23.
 */
public class RegisterActivity extends BaseActivity {

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_info);
    }
}
