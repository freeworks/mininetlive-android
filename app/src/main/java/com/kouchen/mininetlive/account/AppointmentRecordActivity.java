package com.kouchen.mininetlive.account;

import android.view.View;

import com.kouchen.mininetlive.R;

import java.util.List;

/**
 * Created by cainli on 16/7/2.
 */
public class AppointmentRecordActivity extends RecordActivity {
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
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void success(Object list) {
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
