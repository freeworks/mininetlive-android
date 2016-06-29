package com.kouchen.mininetlive.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kouchen.mininetlive.R;

/**
 * Created by cainli on 16/6/26.
 */
public class ItemView extends RelativeLayout {
    private ImageView icon;
    private TextView text;

    public ItemView(Context context) {
        super(context);
    }

    public ItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.itemview_layout, this);
        icon = (ImageView) findViewById(R.id.icon);
        text = (TextView) findViewById(R.id.text);
        TypedArray tTypedArray = null;
        try {
            tTypedArray = context.obtainStyledAttributes(attrs, R.styleable.ItemView);
            icon.setImageDrawable(tTypedArray.getDrawable(R.styleable.ItemView_ItemIcon));
            text.setText(tTypedArray.getText(R.styleable.ItemView_ItemText));
        } finally {
            if (tTypedArray != null)
                tTypedArray.recycle();
        }
    }


    public ItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
