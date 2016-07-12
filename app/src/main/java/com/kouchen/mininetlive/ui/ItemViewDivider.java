package com.kouchen.mininetlive.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.utils.DisplayUtil;

/**
 * Created by cainli on 16/6/26.
 */
public class ItemViewDivider extends View {
    private Paint paint;
    private float leftPading;
    private Rect rect;
    public ItemViewDivider(Context context, AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(Color.WHITE);
        paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(0xFFEEEEEE);
        leftPading = DisplayUtil.dip2px(context,40);
        TypedArray tTypedArray = null;
        try {
            tTypedArray = context.obtainStyledAttributes(attrs, R.styleable.ItemViewDivider);
            leftPading = tTypedArray.getDimension(R.styleable.ItemViewDivider_LeftMargin,0);
        } finally {
            if (tTypedArray != null)
                tTypedArray.recycle();
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        paint.setStrokeWidth(getHeight());
        rect = new Rect(0,0,getWidth(),getHeight());
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.save();
        canvas.translate(leftPading,0);
        canvas.drawRect(rect,paint);
        canvas.restore();
    }
}
