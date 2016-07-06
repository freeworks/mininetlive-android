package com.kouchen.mininetlive.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kouchen.mininetlive.AbsTitlebarFragment;
import com.kouchen.mininetlive.CommonView;
import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.ui.RecycleViewDivider;
import com.kouchen.mininetlive.utils.DisplayUtil;

import java.util.List;

/**
 * Created by cainli on 16/6/24.
 */
public class LiveFragment extends AbsTitlebarFragment implements CommonView {

    private ActivityPresenter presenter;

    private RecyclerView recyclerView;

    private LiveAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ActivityPresenterImpl(this);
        adapter = new LiveAdapter();
    }

    @Override
    public void onDestroyView() {
        presenter.onDestroy();
        super.onDestroyView();
    }

    @Override
    protected void initView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.live_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new RecycleViewDivider(
                getContext(), LinearLayoutManager.VERTICAL, DisplayUtil.dip2px(getContext(),8), getResources().getColor(R.color.divide_gray_color)));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected int getContentViewResId() {
        return R.layout.fragment_live_list;
    }

    @Override
    protected String getTitle() {
        return "直播";
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.getLiveList();
    }

    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void success(Object list) {
        adapter.setData((List<ActivityInfo>)list);
    }
}
