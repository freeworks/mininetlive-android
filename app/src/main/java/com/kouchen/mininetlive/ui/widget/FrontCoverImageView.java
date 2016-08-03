package com.kouchen.mininetlive.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.kouchen.mininetlive.R;
import com.kouchen.mininetlive.utils.DisplayUtil;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * Created by cainli on 16/6/30.
 */
public class FrontCoverImageView extends RoundedImageView {

    private String url;

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
        int heightSize = widthSize * 9 / 16;
        setMeasuredDimension(widthSize, heightSize);
        if (url != null) {
            Glide.with(getContext())
                    .load(url + "?iopcmd=thumbnail&type=8&width=" + widthSize + "&height=" + heightSize)
                    .placeholder(R.drawable.img_default)
                    .transform(new GlideRoundTransform(getContext(), DisplayUtil.dip2px(getContext(), 1.5f)))
                    .into(this);
        }
    }

    public void setUrl(String url) {
        this.url = url;

    }
}
