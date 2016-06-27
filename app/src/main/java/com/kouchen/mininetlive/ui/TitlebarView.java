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

/**
 * Created by cainli on 16/6/25.
 */
public class TitlebarView extends FrameLayout {

    private View backview;
    private TextView titleTextView;
    private TextView rightTextView;
    private Paint paint;

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

    public void init(){
        LayoutInflater.from(getContext()).inflate(R.layout.title_bar, this);
        backview = (TextView) findViewById(R.id.back);
        titleTextView = (TextView) findViewById(R.id.title);
        rightTextView = (TextView) findViewById(R.id.right);
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0x8AB2B2B2);
        paint.setStrokeWidth(DensityUtil.dip2px(getContext(),1));
    }

    public void setTitle(String title) {
        titleTextView.setText(title);
    }

    public void setBackLister(OnClickListener onClickListener){
        if (onClickListener != null){
            backview.setVisibility(View.VISIBLE);
            backview.setOnClickListener(onClickListener);
        }else{
            backview.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.save();
        canvas.drawLine(0,getHeight(),getWidth(),getHeight(),paint);
        canvas.restore();
    }
}
