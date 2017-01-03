package com.kouchen.mininetlive.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kouchen.mininetlive.MNLApplication;
import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.contracts.AccountContract;
import com.kouchen.mininetlive.di.components.DaggerAccountComponent;
import com.kouchen.mininetlive.di.modules.AccountModule;
import com.kouchen.mininetlive.models.DividendRecordInfo;
import com.kouchen.mininetlive.presenter.AccountPresenter;
import com.kouchen.mininetlive.ui.base.AbsTitlebarActivity;
import com.kouchen.mininetlive.ui.widget.RecycleViewDivider;
import com.kouchen.mininetlive.utils.DisplayUtil;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cainli on 2016/11/18.
 */
public class DividendListActivity extends AbsTitlebarActivity implements AccountContract.View {

    @BindView(R.id.list)
    RecyclerView recyclerView;

    @Inject
    AccountPresenter presenter;

    protected DividendRecordAdapter adapter;

    @Override
    protected void initInject() {
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
                presenter.getDividendRecordList();
            }
        });
        noDateView.setup("暂无记录");
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter = new DividendRecordAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new RecycleViewDivider(
                this,
                LinearLayoutManager.VERTICAL,
                DisplayUtil.dip2px(this, 1),
                getResources().getColor(R.color.divide_gray_color),
                DisplayUtil.dip2px(this, 15), 0));
        presenter.getDividendRecordList();
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_record;
    }


    @Override
    public String getTitleString() {
        return "邀请有奖";
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
            List<DividendRecordInfo> records = (List<DividendRecordInfo>) data;
            adapter.setData(records);
            if(records == null || records.isEmpty()){
                noDateView.setVisibility(View.VISIBLE);
            }else{
                noDateView.setVisibility(View.GONE);
            }
        }
    }

    public static class DividendRecordAdapter extends RecyclerView.Adapter<DividendRecordViewHolder> {

        private List<DividendRecordInfo> recordInfoList;

        @Override
        public DividendRecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dividend_layout, parent, false);
            return new DividendRecordViewHolder(view);
        }

        @Override
        public void onBindViewHolder(DividendRecordViewHolder holder, int position) {
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

        public void setData(List<DividendRecordInfo> recordInfoList) {
            this.recordInfoList = recordInfoList;
            notifyDataSetChanged();
        }
    }

    public static class DividendRecordViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.amount)
        TextView amount;
        @BindView(R.id.datetime)
        TextView date;
        @BindView(R.id.title)
        TextView title;

        public DividendRecordViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setData(DividendRecordInfo r) {
            title.setText(r.getNickname() + "  支付了一个活动");
            amount.setText("￥" + r.getAmount());
            assert date != null;
            date.setText(r.getCreateTime());
        }
    }
}
