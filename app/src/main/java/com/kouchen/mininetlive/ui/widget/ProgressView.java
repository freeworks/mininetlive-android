package com.kouchen.mininetlive.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kouchen.mininetlive.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cainli on 16/7/17.
 */
public class ProgressView extends RelativeLayout {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.text)
    TextView text;

    public ProgressView(Context context) {
        this(context, null);
    }

    public ProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.progress_bar, this);
        ButterKnife.bind(this);
    }

    public void setText(String text) {
        this.text.setText(text);
    }
}
