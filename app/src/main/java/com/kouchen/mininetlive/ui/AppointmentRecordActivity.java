package com.kouchen.mininetlive.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import com.kouchen.mininetlive.MNLApplication;
import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.di.components.DaggerAccountComponent;
import com.kouchen.mininetlive.di.modules.AccountModule;
import com.kouchen.mininetlive.presenter.AccountPresenter;
import com.kouchen.mininetlive.models.AppointmentRecordInfo;
import com.kouchen.mininetlive.models.RecordInfo;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by cainli on 16/7/2.
 */
public class AppointmentRecordActivity extends RecordActivity {

    @Inject
    AccountPresenter presenter;

    @Override
    protected void initInject() {
        DaggerAccountComponent.builder()
                .accountModule(new AccountModule(this))
                .netComponent(((MNLApplication) getApplication()).getNetComponent())
                .build()
                .inject(this);
    }

    @Override
    public String getTitleString() {
        return "预约记录";
    }


    @Override
    protected void loadData() {
        presenter.getAppointRecordList();
    }

    @Override
    protected RecordAdapter getAdapter() {
        return new AppointmentRecordAdapter();
    }


    @Override
    public void onSuccess(Object list) {
        super.onSuccess(list);
        if (adapter != null) {
            adapter.setData((List<? extends RecordInfo>) list);
        }
    }

    public static class AppointmentRecordAdapter extends RecordAdapter<AppointmentRecordInfo> {

        @Override
        protected RecordViewHolder createViewHolder(View view) {
            return new RecordViewHolder(view);
        }

        @Override
        protected int getItemLayoutResId() {
            return R.layout.item_appointment_layout;
        }
    }
}
