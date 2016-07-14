package com.kouchen.mininetlive.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.kouchen.mininetlive.MNLApplication;
import com.kouchen.mininetlive.di.components.DaggerActivityComponent;
import com.kouchen.mininetlive.di.modules.ActivityModule;
import com.kouchen.mininetlive.ui.base.AbsTitlebarFragment;
import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.contracts.ActivityContract;
import com.kouchen.mininetlive.presenter.ActivityPresenter;
import com.kouchen.mininetlive.models.ActivityInfo;
import com.kouchen.mininetlive.ui.widget.RecycleViewDivider;
import com.kouchen.mininetlive.utils.DisplayUtil;
import java.util.List;
import javax.inject.Inject;

/**
 * Created by cainli on 16/6/24.
 */
public class LiveFragment extends AbsTitlebarFragment implements ActivityContract.View {

    @Inject
    ActivityPresenter presenter;

    private RecyclerView recyclerView;

    private LiveAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerActivityComponent.builder()
            .activityModule(new ActivityModule(this))
            .netComponent(((MNLApplication)getActivity().getApplication()).getNetComponent())
            .build()
            .inject(this);
        adapter = new LiveAdapter();
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
    public void onError(String msg) {

    }

    @Override
    public void onSuccess(Object data) {

    }

    @Override
    public void onInitLoadSuccess(Object data) {
        adapter.setData((List<ActivityInfo>)data);
    }

    @Override
    public void onLoadMoreSuccess(List<ActivityInfo> activityInfos, boolean hasmore) {

    }
}
