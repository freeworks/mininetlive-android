package com.kouchen.mininetlive.account;


import com.kouchen.mininetlive.CommonView;

/**
 * Created by cainli on 16/6/25.
 */
public class AccountPresenterImpl implements AccountPresenter, AccountInteractor.OnAccountFinishedListener {

    private CommonView commonView;
    private AccountInteractor accountInteractor;

    public AccountPresenterImpl(CommonView commonView) {
        this.commonView = commonView;
        this.accountInteractor = new AccountInteractorImpl();
    }

    @Override
    public void getPlayRecordList() {
        if (commonView != null) {
            commonView.showProgress();
        }
        accountInteractor.getPlayRecordList(this);
    }

    @Override
    public void getPayRecordList() {
        if (commonView != null) {
            commonView.showProgress();
        }
        accountInteractor.getPayRecordList(this);
    }

    @Override
    public void getAppointRecordList() {
        if (commonView != null) {
            commonView.showProgress();
        }
        accountInteractor.getAppointRecordList(this);
    }

    @Override
    public void getAccountInfo() {

    }

    @Override
    public void onDestroy() {
        commonView = null;
    }

    @Override
    public void onSuccess(Object object) {
        if(commonView!=null){
            commonView.success(object);
        }
    }

    @Override
    public void onError(String msg) {

    }
}
