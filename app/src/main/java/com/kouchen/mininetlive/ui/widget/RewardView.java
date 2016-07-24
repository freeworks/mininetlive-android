package com.kouchen.mininetlive.ui.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kouchen.mininetlive.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by cainli on 16/7/24.
 */
public class RewardView extends RelativeLayout {

    @BindView(R.id.amount0)
    TextView amount0;
    @BindView(R.id.amount1)
    TextView amount1;
    @BindView(R.id.amount2)
    TextView amount2;
    @BindView(R.id.amount3)
    TextView amount3;

    TextView btnArray[];

    private View selectView;

    public RewardView(Context context) {
        this(context, null);
    }

    public RewardView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RewardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.reward_view_layout, this);
        ButterKnife.bind(this, this);
        btnArray = new TextView[4];
        amount0.setTag(188);
        btnArray[0] = amount0;
        amount1.setTag(888);
        btnArray[1] = amount1;
        amount2.setTag(6888);
        btnArray[2] = amount2;
        amount3.setTag(8888);
        btnArray[3] = amount3;
        selectView = amount0;
        amount0.setTextColor(Color.WHITE);
        amount0.setBackgroundResource(R.drawable.reward_amount_selected);
    }

    @OnClick({R.id.amount0, R.id.amount1, R.id.amount2, R.id.amount3})
    public void onclick(View view) {
        selectView = view;
        for (TextView v : btnArray) {
            if (v == view) {
                v.setTextColor(Color.WHITE);
                v.setBackgroundResource(R.drawable.reward_amount_selected);
            } else {
                v.setTextColor(0xFFE97512);
                v.setBackgroundResource(R.drawable.reward_amount_unselected);
            }
        }
    }

    public int getAmount() {
        return (int) selectView.getTag();
    }
}
