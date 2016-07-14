package com.kouchen.mininetlive.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import butterknife.BindView;

import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.contracts.AccountContract;
import com.kouchen.mininetlive.models.RecordInfo;
import com.kouchen.mininetlive.ui.base.AbsTitlebarActivity;
import com.kouchen.mininetlive.ui.widget.RecycleViewDivider;
import com.kouchen.mininetlive.utils.DisplayUtil;

import java.util.List;

/**
 * Created by cainli on 16/7/2.
 */
public abstract class RecordActivity<T extends RecordInfo> extends AbsTitlebarActivity implements
        AccountContract.View {

    @BindView(R.id.list)
    RecyclerView recyclerView;

    protected RecordAdapter adapter;


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
        showProgressView();
    }

    @Override
    public void hideProgress() {
        hideProgressView();
    }

    @Override
    public void onError(String msg) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSuccess(Object list) {
        hideProgressView();
        if (adapter != null) {
            adapter.setData((List<T>) list);
        }
    }

}


