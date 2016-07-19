package com.kouchen.mininetlive.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.kouchen.mininetlive.MNLApplication;
import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.contracts.ActivityContract;
import com.kouchen.mininetlive.di.components.DaggerActivityComponent;
import com.kouchen.mininetlive.di.modules.ActivityModule;
import com.kouchen.mininetlive.models.ActivityInfo;
import com.kouchen.mininetlive.presenter.ActivityPresenter;
import com.kouchen.mininetlive.ui.base.AbsTitlebarFragment;
import com.kouchen.mininetlive.ui.widget.NetErrorView;
import com.kouchen.mininetlive.ui.widget.RecycleViewDivider;
import com.kouchen.mininetlive.utils.DisplayUtil;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.finalteam.loadingviewfinal.OnDefaultRefreshListener;
import cn.finalteam.loadingviewfinal.PtrClassicFrameLayout;
import cn.finalteam.loadingviewfinal.PtrFrameLayout;
import cn.finalteam.loadingviewfinal.RecyclerViewFinal;

/**
 * Created by cainli on 16/6/24.
 */
public class LiveFragment extends AbsTitlebarFragment implements ActivityContract.View {

    @Inject
    ActivityPresenter presenter;

    @BindView(R.id.ptr_rv_layout)
    PtrClassicFrameLayout mPtrRvLayout;
    @BindView(R.id.live_recyclerview)
    RecyclerViewFinal recyclerView;

    private LiveAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .netComponent(((MNLApplication) getActivity().getApplication()).getNetComponent())
                .build()
                .inject(this);
        adapter = new LiveAdapter();
        presenter.getLiveList();
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        recyclerView = (RecyclerViewFinal) view.findViewById(R.id.live_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new RecycleViewDivider(
                getContext(), LinearLayoutManager.VERTICAL, DisplayUtil.dip2px(getContext(), 8), getResources().getColor(R.color.divide_gray_color)));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        mPtrRvLayout.setOnRefreshListener(new OnDefaultRefreshListener() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                presenter.getLiveList();
            }
        });
        netErrView.setup(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.getLiveList();
                showProgress();
            }
        });
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
    public void showProgress() {
        showProgressView();
    }

    @Override
    public void hideProgress() {
        hideProgressView();
    }

    @Override
    public void onError(String msg) {
        adapter.setData(null);
        mPtrRvLayout.onRefreshComplete();//完成下拉刷新
        netErrView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccess(Object data) {

    }

    @Override
    public void onInitLoadSuccess(Object data) {
        mPtrRvLayout.onRefreshComplete();
        List<ActivityInfo> activityInfos = (List<ActivityInfo>) data;
        adapter.setData(activityInfos);
        if (activityInfos == null || activityInfos.isEmpty()) {
            noDateView.setVisibility(View.VISIBLE);
        } else {
            noDateView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoadMoreSuccess(List<ActivityInfo> activityInfos, boolean hasmore) {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
