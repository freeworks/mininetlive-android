package com.kouchen.mininetlive.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kouchen.mininetlive.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cainli on 16/7/17.
 */
public class NetErrorView extends RelativeLayout {

    @BindView(R.id.icon)
    ImageView icon;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.errLayout)
    RelativeLayout errLayout;

    public NetErrorView(Context context) {
        this(context, null);
    }

    public NetErrorView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NetErrorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.net_err_view, this);
        ButterKnife.bind(this);
    }

    public void setup(int iconresId, String text, OnClickListener onClickListener) {
        icon.setImageResource(iconresId);
        this.text.setText(text);
        errLayout.setOnClickListener(onClickListener);
    }

    public void setup(final OnClickListener onClickListener) {
        errLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                NetErrorView.this.setVisibility(View.INVISIBLE);
                onClickListener.onClick(view);
            }
        });
    }

    public void setVisibility(int visible, String s) {
        this.text.setText(s);
        setVisibility(visible);
    }
}
