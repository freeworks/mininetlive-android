package com.kouchen.mininetlive.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kouchen.mininetlive.MNLApplication;
import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.contracts.LiveContract;
import com.kouchen.mininetlive.di.components.DaggerLiveComponent;
import com.kouchen.mininetlive.di.modules.LiveModule;
import com.kouchen.mininetlive.models.ActivityInfo;
import com.kouchen.mininetlive.presenter.LivePresenter;
import com.kouchen.mininetlive.ui.base.AbsTitlebarFragment;

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
public class LiveFragment extends AbsTitlebarFragment implements LiveContract.View {

    @Inject
    LivePresenter presenter;

    @BindView(R.id.ptr_rv_layout)
    PtrClassicFrameLayout mPtrRvLayout;
    @BindView(R.id.live_recyclerview)
    RecyclerViewFinal recyclerView;

    private LiveAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerLiveComponent.builder()
                .liveModule(new LiveModule(this))
                .netComponent(((MNLApplication) getActivity().getApplication()).getNetComponent())
                .build()
                .inject(this);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        recyclerView = (RecyclerViewFinal) view.findViewById(R.id.live_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);
        adapter = new LiveAdapter();
        recyclerView.setAdapter(adapter);
        mPtrRvLayout.setOnRefreshListener(new OnDefaultRefreshListener() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                presenter.refresh();
            }
        });
        netErrView.setup(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgress();
                presenter.initLoad();
            }
        });
        showProgress();
        presenter.initLoad();
    }

    @Override
    public void show() {
        super.show();
        if(presenter!=null){
            presenter.initLoad();
        }
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onLoadSuccess(List<ActivityInfo> activityInfos) {
        mPtrRvLayout.onRefreshComplete();
        adapter.setData(activityInfos);
        if (activityInfos == null || activityInfos.isEmpty()) {
            noDateView.setVisibility(View.VISIBLE);
        } else {
            noDateView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onInitLoadError(String msg) {
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
        netErrView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRefreshError(String msg) {
        mPtrRvLayout.onRefreshComplete();
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }
}
