package com.kouchen.mininetlive.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kouchen.mininetlive.models.RecordInfo;
import java.util.List;

/**
 * Created by cainli on 16/6/25.
 */
public abstract class RecordAdapter<T extends RecordInfo> extends RecyclerView.Adapter<RecordViewHolder> {

    private List<? extends RecordInfo> recordInfoList;

    @Override
    public RecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getItemLayoutResId(), parent, false);
        return createViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecordViewHolder holder, int position) {
        if (holder == null) {
            return;
        }
        holder.setData(recordInfoList.get(position));
    }

    @Override
    public int getItemCount() {
        if (recordInfoList == null) {
            return 0;
        }
        return recordInfoList.size();
    }

    public void setData(List<T> recordInfoList) {
        this.recordInfoList = recordInfoList;
        notifyDataSetChanged();
    }


    protected abstract RecordViewHolder createViewHolder(View view);

    protected abstract int getItemLayoutResId();


}

