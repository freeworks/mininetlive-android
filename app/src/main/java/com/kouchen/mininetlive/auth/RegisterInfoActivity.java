package com.kouchen.mininetlive.auth;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioGroup;

import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.base.BaseActivity;

/**
 * Created by cainli on 16/6/23.
 */
public class RegisterInfoActivity extends BaseActivity implements AuthView {

    private ProgressBar progressBar;

    public EditText nickname;

    private AuthPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_info);
        nickname = (EditText) findViewById(R.id.nickname);
        ((RadioGroup) findViewById(R.id.genderRadioGroup)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {

            }
        });
        progressBar = (ProgressBar) findViewById(R.id.progress);
        presenter = new AuthPresenterImpl(this);
    }

    @Override
    protected void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    public void onClick(View view) {
        if(view.getId() == R.id.submitBtn){
//            presenter.register();
        }
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }


    @Override
    public void navigateToHome() {

    }

    @Override
    public void setError(String msg) {

    }

    @Override
    public void onSubmitVCodeSuccess() {

    }

    @Override
    public void onGetVCodeSuccess() {

    }

    @Override
    public void toRegisterInfo() {
    }
}
