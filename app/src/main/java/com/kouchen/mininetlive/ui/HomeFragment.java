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
import com.kouchen.mininetlive.contracts.HomeContract;
import com.kouchen.mininetlive.di.components.DaggerHomeComponent;
import com.kouchen.mininetlive.di.modules.HomeModule;
import com.kouchen.mininetlive.models.ActivityInfo;
import com.kouchen.mininetlive.models.HomeModel;
import com.kouchen.mininetlive.presenter.HomePresenter;
import com.kouchen.mininetlive.ui.base.AbsTitlebarFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.finalteam.loadingviewfinal.OnDefaultRefreshListener;
import cn.finalteam.loadingviewfinal.OnLoadMoreListener;
import cn.finalteam.loadingviewfinal.PtrClassicFrameLayout;
import cn.finalteam.loadingviewfinal.PtrFrameLayout;
import cn.finalteam.loadingviewfinal.RecyclerViewFinal;

/**
 * Created by cainli on 16/6/24.
 */
public class HomeFragment extends AbsTitlebarFragment implements HomeContract.View {

    @Inject
    HomePresenter presenter;

    HomeAdapter adapter;

    @BindView(R.id.ptr_rv_layout)
    PtrClassicFrameLayout mPtrRvLayout;

    @BindView(R.id.live_recyclerview)
    RecyclerViewFinal recyclerViewFinal;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerHomeComponent.builder()
                .homeModule(new HomeModule(this))
                .netComponent(((MNLApplication) getActivity().getApplication()).getNetComponent())
                .build()
                .inject(this);
        adapter = new HomeAdapter();
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        recyclerViewFinal.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewFinal.setHasFixedSize(true);
        recyclerViewFinal.setAdapter(adapter);

        mPtrRvLayout.setOnRefreshListener(new OnDefaultRefreshListener() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                presenter.refresh();
            }
        });
        recyclerViewFinal.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                presenter.loadMore(adapter.getLastItemId());
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
        return R.layout.fragment_homepage;
    }

    @Override
    protected String getTitle() {
        return "首页";
    }


    @Override
    public void showProgress() {
        netErrView.setVisibility(View.INVISIBLE);
        noDateView.setVisibility(View.INVISIBLE);
        showProgressView();
    }

    @Override
    public void hideProgress() {
        hideProgressView();
    }

    @Override
    public void onError(String msg) {
        recyclerViewFinal.onLoadMoreComplete();
        netErrView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccess(Object data) {
    }

    @Override
    public void onRefreshError(String msg) {
        mPtrRvLayout.onRefreshComplete();
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadMoreError(String msg) {
        recyclerViewFinal.onLoadMoreComplete();
        Toast.makeText(getContext(),msg,Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onLoadMoreSuccess(List<ActivityInfo> activityInfos, boolean hasmore) {
        if (activityInfos == null) {
            return;
        }
        adapter.appendGeneralData(activityInfos);
        recyclerViewFinal.setHasLoadMore(hasmore);
        recyclerViewFinal.onLoadMoreComplete();
    }

    @Override
    public void onLoadSuccess(HomeModel homeModel) {
        mPtrRvLayout.onRefreshComplete();
        if(homeModel.getCount() <= 0){
            if(adapter.getItemCount() <=0){
                noDateView.setVisibility(View.VISIBLE);
            }else{
                noDateView.setVisibility(View.GONE);
            }
        }else{
            adapter.setData(homeModel);
        }
        recyclerViewFinal.setHasLoadMore(homeModel.hasMore);
        mPtrRvLayout.onRefreshComplete();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }
}
