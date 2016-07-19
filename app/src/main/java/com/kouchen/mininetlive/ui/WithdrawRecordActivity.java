package com.kouchen.mininetlive.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.kouchen.mininetlive.MNLApplication;
import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.di.components.DaggerAccountComponent;
import com.kouchen.mininetlive.contracts.AccountContract;
import com.kouchen.mininetlive.di.modules.AccountModule;
import com.kouchen.mininetlive.presenter.AccountPresenter;
import com.kouchen.mininetlive.models.WithdrawRecordInfo;
import com.kouchen.mininetlive.ui.base.AbsTitlebarActivity;
import com.kouchen.mininetlive.ui.widget.RecycleViewDivider;
import com.kouchen.mininetlive.utils.DisplayUtil;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by cainli on 16/7/2.
 */
public class WithdrawRecordActivity extends AbsTitlebarActivity implements AccountContract.View {

    @BindView(R.id.list)
    RecyclerView recyclerView;

    @Inject
    AccountPresenter presenter;

    protected WithdrawRecordAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerAccountComponent.builder()
                .accountModule(new AccountModule(this))
                .netComponent(((MNLApplication) getApplication()).getNetComponent())
                .build()
                .inject(this);
    }

    @Override
    protected void initView(View contentView) {
        netErrView.setup(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.getWithdrawRecordList();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter = new WithdrawRecordAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RecycleViewDivider(
                this,
                LinearLayoutManager.VERTICAL,
                DisplayUtil.dip2px(this, 1),
                getResources().getColor(R.color.divide_gray_color),
                DisplayUtil.dip2px(this, 15), 0));
        presenter.getWithdrawRecordList();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_record;
    }


    @Override
    public String getTitleString() {
        return "提现明细";
    }

    @Override
    public void showProgress() {
        showProgressView("获取中...");
    }

    @Override
    public void hideProgress() {
        hideProgressView();
    }

    @Override
    public void onError(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        netErrView.setVisibility(View.VISIBLE);
    }
    @Override
    public void onSuccess(Object data) {
        if (adapter != null) {
            adapter.setData((List<WithdrawRecordInfo>) data);
        }
    }

    public static class WithdrawRecordAdapter extends RecyclerView.Adapter<WithdrawRecordViewHolder> {

        private List<WithdrawRecordInfo> recordInfoList;

        @Override
        public WithdrawRecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_withdraw_layout, parent, false);
            return new WithdrawRecordViewHolder(view);
        }

        @Override
        public void onBindViewHolder(WithdrawRecordViewHolder holder, int position) {
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

        public void setData(List<WithdrawRecordInfo> recordInfoList) {
            this.recordInfoList = recordInfoList;
            notifyDataSetChanged();
        }
    }

    public static class WithdrawRecordViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.amount)
        TextView amount;
        @BindView(R.id.date)
        TextView date;

        public WithdrawRecordViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(WithdrawRecordInfo r) {
            amount.setText(String.valueOf(r.getAmount()));
            assert date != null;
            date.setText(r.getCreateTime());
        }
    }

}
