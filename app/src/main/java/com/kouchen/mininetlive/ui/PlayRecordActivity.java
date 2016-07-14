package com.kouchen.mininetlive.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import com.kouchen.mininetlive.MNLApplication;
import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.di.components.DaggerAccountComponent;
import com.kouchen.mininetlive.di.modules.AccountModule;
import com.kouchen.mininetlive.presenter.AccountPresenter;
import com.kouchen.mininetlive.models.PlayRecordInfo;
import com.kouchen.mininetlive.models.RecordInfo;
import javax.inject.Inject;

/**
 * Created by cainli on 16/7/2.
 */
public class PlayRecordActivity extends RecordActivity {

    @Inject
    AccountPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerAccountComponent.builder()
            .accountModule(new AccountModule(this))
            .netComponent(((MNLApplication) getApplication()).getNetComponent())
            .build()
            .inject(this);
    }

    @Override
    public String getTitleString() {
        return "播放记录";
    }

    @Override
    protected void loadData() {
        presenter.getPlayRecordList();
    }

    @Override
    protected RecordAdapter getAdapter() {
        return new PlayRecordAdapter();
    }

    public static class PlayRecordAdapter extends RecordAdapter<PlayRecordInfo> {
        @Override
        protected RecordViewHolder createViewHolder(View view) {
            return new PlayRecordViewHolder(view);
        }

        @Override
        protected int getItemLayoutResId() {
            return R.layout.item_play_layout;
        }

    }

    public static class PlayRecordViewHolder extends RecordViewHolder {

        @BindView(R.id.playCount)
        TextView playCount;

        public PlayRecordViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void setData(RecordInfo r) {
            super.setData(r);
            playCount.setText(String.valueOf(((PlayRecordInfo) r).getPlayCount()));
        }
    }

}
