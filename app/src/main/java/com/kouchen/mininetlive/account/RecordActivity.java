package com.kouchen.mininetlive.account;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kouchen.mininetlive.AbsTitlebarActivity;
import com.kouchen.mininetlive.ActivityView;
import com.kouchen.mininetlive.CommonView;
import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.activity.ActivityInfo;
import com.kouchen.mininetlive.activity.HomeModel;
import com.kouchen.mininetlive.ui.RecycleViewDivider;
import com.kouchen.mininetlive.utils.DisplayUtil;

import java.util.List;

import butterknife.BindView;

/**
 * Created by cainli on 16/7/2.
 */
public abstract class RecordActivity<T extends RecordInfo> extends AbsTitlebarActivity implements CommonView {

    @BindView(R.id.list)
    RecyclerView recyclerView;

    protected AccountPresenter presenter;

    protected RecordAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new AccountPresenterImpl(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter = getAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RecycleViewDivider(
                this,
                LinearLayoutManager.VERTICAL,
                DisplayUtil.dip2px(this, 1),
                getResources().getColor(R.color.divide_gray_color),
                DisplayUtil.dip2px(this, 15), 0));
        loadData();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_record;
    }

    protected abstract void loadData();

    protected void setData(List<? extends RecordInfo> data) {
        if (adapter != null) {
            adapter.setData(data);
        }
    }

    protected abstract RecordAdapter getAdapter();


    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onFail() {

    }


    @Override
    public void success(Object list) {
        if (adapter != null) {
            adapter.setData((List<T>) list);
        }
    }

}


