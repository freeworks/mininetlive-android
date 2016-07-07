package com.kouchen.mininetlive.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.makeramen.roundedimageview.RoundedImageView;

/**
 * Created by cainli on 16/6/30.
 */
public class FrontCoverImageView extends RoundedImageView {

    public FrontCoverImageView(Context context) {
        super(context);
        setScaleType(ScaleType.CENTER_CROP);
    }

    public FrontCoverImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setScaleType(ScaleType.CENTER_CROP);
    }

    public FrontCoverImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setScaleType(ScaleType.CENTER_CROP);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = widthSize *9/16;
        setMeasuredDimension(widthSize,heightSize);
    }
}
