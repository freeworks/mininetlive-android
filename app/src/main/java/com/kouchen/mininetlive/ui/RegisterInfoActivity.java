package com.kouchen.mininetlive.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import com.kouchen.mininetlive.MNLApplication;
import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.di.components.DaggerAuthComponent;
import com.kouchen.mininetlive.contracts.AuthContract;
import com.kouchen.mininetlive.di.modules.AuthModule;
import com.kouchen.mininetlive.presenter.AuthPresenter;
import com.kouchen.mininetlive.ui.base.BaseActivity;
import javax.inject.Inject;

/**
 * Created by cainli on 16/6/23.
 */
public class RegisterInfoActivity extends BaseActivity implements AuthContract.View {

    private ProgressBar progressBar;

    public EditText nickname;

    @Inject
    AuthPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerAuthComponent.builder()
            .authModule(new AuthModule(this))
            .netComponent(((MNLApplication) getApplication()).getNetComponent())
            .build()
            .inject(this);
        setContentView(R.layout.activity_register_info);
        nickname = (EditText) findViewById(R.id.nickname);
        ((RadioGroup) findViewById(R.id.genderRadioGroup)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

            }
        });
        progressBar = (ProgressBar) findViewById(R.id.progress);
    }


    public void onClick(View view) {
        if(view.getId() == R.id.submitBtn){
        }
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onError(String msg) {

    }

    @Override
    public void onSuccess(Object data) {

    }

}
