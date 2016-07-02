package com.kouchen.mininetlive.account;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.kouchen.mininetlive.AbsTitlebarActivity;
import com.kouchen.mininetlive.R;

import butterknife.BindView;

/**
 * Created by cainli on 16/7/2.
 */
public abstract class RecordActivity extends AbsTitlebarActivity {

    @BindView(R.id.list)
    RecyclerView recyclerView;

    private RecordAdapter adapter;


    @Override
    protected void onResume() {
        super.onResume();
        adapter = new RecordAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected int getContentResId() {
        return R.layout.activity_record;
    }
}


