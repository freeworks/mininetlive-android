package com.kouchen.mininetlive.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hyphenate.util.DensityUtil;
import com.kouchen.mininetlive.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cainli on 16/6/25.
 */
public class TitlebarView extends FrameLayout {

    @BindView(R.id.back)
    View backview;
    @BindView(R.id.backImageView)
    View backImageView;
    @BindView(R.id.title)
    TextView titleTextView;
    @BindView(R.id.right)
    TextView rightTextView;
    private Paint paint;
    private boolean transparentBackgroud;

    public TitlebarView(Context context) {
        super(context);
        init();

    }

    public TitlebarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TitlebarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init() {
        setBackgroundResource(R.color.titlebarBg);
        LayoutInflater.from(getContext()).inflate(R.layout.title_bar, this);
        ButterKnife.bind(this,this);
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0x8AB2B2B2);
        paint.setStrokeWidth(DensityUtil.dip2px(getContext(), 1));
    }


    public void setTransparentBackground(boolean b) {
        if(b){
            setBackgroundResource(R.color.transparent);
            transparentBackgroud = b;
        }else{
            setBackgroundResource(R.color.titlebarBg);
            transparentBackgroud = !b;
        }
    }

    public void setTitle(String title) {
        titleTextView.setText(title);
    }

    public void setBackLister(OnClickListener onClickListener) {
        setBackLister(onClickListener,true);
    }

    public void setBackLister(OnClickListener onClickListener,boolean isText) {
        if (onClickListener != null) {
            if(!isText){
                backview.setVisibility(View.INVISIBLE);
                backImageView.setVisibility(View.VISIBLE);
                backImageView.setOnClickListener(onClickListener);
            }else{
                backImageView.setVisibility(View.INVISIBLE);
                backview.setVisibility(View.VISIBLE);
                backview.setOnClickListener(onClickListener);
            }
        } else {
            backview.setVisibility(View.INVISIBLE);
            backImageView.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        if (!transparentBackgroud) {
            canvas.save();
            canvas.drawLine(0, getHeight(), getWidth(), getHeight(), paint);
            canvas.restore();
        }
    }


}
