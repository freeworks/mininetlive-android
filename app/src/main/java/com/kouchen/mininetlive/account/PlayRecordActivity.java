package com.kouchen.mininetlive.account;

import android.view.View;
import com.kouchen.mininetlive.R;


/**
 * Created by cainli on 16/7/2.
 */
public class PlayRecordActivity extends RecordActivity {

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
}
