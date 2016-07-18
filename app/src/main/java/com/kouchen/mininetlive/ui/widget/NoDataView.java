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
public class NoDataView extends RelativeLayout {

    @BindView(R.id.icon)
    ImageView icon;
    @BindView(R.id.text)
    TextView text;

    public NoDataView(Context context) {
        this(context, null);
    }

    public NoDataView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NoDataView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.no_date_view, this);
        ButterKnife.bind(this);
    }

    public void setup(String text) {
        this.text.setText(text);
    }
}
