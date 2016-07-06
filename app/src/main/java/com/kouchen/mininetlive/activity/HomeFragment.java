package com.kouchen.mininetlive.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.kouchen.mininetlive.AbsTitlebarFragment;
import com.kouchen.mininetlive.ActivityView;
import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.ui.RecycleViewDivider;
import com.kouchen.mininetlive.utils.DisplayUtil;

import java.util.List;

import butterknife.BindView;
import cn.finalteam.loadingviewfinal.OnDefaultRefreshListener;
import cn.finalteam.loadingviewfinal.OnLoadMoreListener;
import cn.finalteam.loadingviewfinal.PtrClassicFrameLayout;
import cn.finalteam.loadingviewfinal.PtrFrameLayout;
import cn.finalteam.loadingviewfinal.RecyclerViewFinal;

/**
 * Created by cainli on 16/6/24.
 */
public class HomeFragment extends AbsTitlebarFragment implements ActivityView {

    private ActivityPresenter presenter;

    private HomeAdapter adapter;

    @BindView(R.id.ptr_rv_layout)
    PtrClassicFrameLayout mPtrRvLayout;

    @BindView(R.id.live_recyclerview)
    RecyclerViewFinal recyclerViewFinal;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ActivityPresenterImpl(this);
        adapter = new HomeAdapter();
    }

    @Override
    protected void initView(View view) {
        recyclerViewFinal.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewFinal.addItemDecoration(new RecycleViewDivider(
                getContext(), LinearLayoutManager.VERTICAL, DisplayUtil.dip2px(getContext(), 8), getResources().getColor(R.color.divide_gray_color)));
        recyclerViewFinal.setHasFixedSize(true);
        recyclerViewFinal.setAdapter(adapter);

        mPtrRvLayout.setOnRefreshListener(new OnDefaultRefreshListener() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                presenter.getHomeList();
            }
        });
        recyclerViewFinal.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void loadMore() {
                presenter.loadMore(adapter.getLastItemId());
            }
        });
    }


    @Override
    public void onDestroyView() {
        presenter.onDestroy();
        super.onDestroyView();
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
    public void onStart() {
        super.onStart();
        presenter.getHomeList();
    }

    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void onFail() {
        mPtrRvLayout.onRefreshComplete();//完成下拉刷新
        recyclerViewFinal.onLoadMoreComplete();
    }

    @Override
    public void success(Object object) {

    }

    @Override
    public void getHomeListSuccess(HomeModel homeModel) {
        adapter.setData(homeModel);
        recyclerViewFinal.setHasLoadMore(homeModel.hasMore);
        mPtrRvLayout.onRefreshComplete();//完成下拉刷新
    }

    @Override
    public void loadMoreSuccess(List<ActivityInfo> list, boolean hasmore) {
        if (list == null) {
            return;
        }
        adapter.appendGeneralData(list);
        recyclerViewFinal.setHasLoadMore(hasmore);
        recyclerViewFinal.onLoadMoreComplete();
    }

    @Override
    public void getLiveListSuccess(List<ActivityInfo> list) {

    }

}
