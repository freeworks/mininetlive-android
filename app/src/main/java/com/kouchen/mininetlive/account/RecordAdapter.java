package com.kouchen.mininetlive.account;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kouchen.mininetlive.R;

import java.util.List;

/**
 * Created by cainli on 16/6/25.
 */
public class RecordAdapter extends RecyclerView.Adapter<RecordAdapter.RecordViewHolder> {

    private List<RecordInfo> recordInfoList;
    @Override
    public RecordAdapter.RecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_activity2, parent, false);
        return new RecordViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecordViewHolder holder, int position) {
        if (holder == null) {
            return;
        }
        RecordInfo recordInfo = recordInfoList.get(position);
        holder.setData(recordInfo);
    }

    @Override
    public int getItemCount() {
        if (recordInfoList == null) {
            return 0;
        }
        return recordInfoList.size();
    }

    public void setData(List<RecordInfo> recordInfoList) {
        this.recordInfoList = recordInfoList;
        notifyDataSetChanged();
    }

    static class RecordViewHolder extends RecyclerView.ViewHolder {

        public RecordViewHolder(View itemView) {
            super(itemView);
        }

        public void setData(RecordInfo recordInfo) {

        }
    }

}


