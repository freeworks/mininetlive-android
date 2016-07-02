package com.kouchen.mininetlive.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.request.target.Target;
import com.kouchen.mininetlive.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cainli on 16/6/26.
 */
public class ItemView2 extends RelativeLayout {

    @BindView(R.id.avatar)
    ImageView avatar;
    @BindView(R.id.key)
    TextView key;
    @BindView(R.id.value)
    TextView value;
    @BindView(R.id.arrow)
    ImageView arrow;
    @BindView(R.id.check)
    ImageView check;

    public ItemView2(Context context) {
        super(context);
    }

    public ItemView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.itemview_layout2, this);
        ButterKnife.bind(this, this);
        TypedArray tTypedArray = null;
        try {
            tTypedArray = context.obtainStyledAttributes(attrs, R.styleable.ItemView2);
            value.setText(tTypedArray.getText(R.styleable.ItemView2_ItemValue));
            key.setText(tTypedArray.getText(R.styleable.ItemView2_ItemKey));
            boolean aBoolean = tTypedArray.getBoolean(R.styleable.ItemView2_IsShowArrow, true);
            arrow.setVisibility(aBoolean ? View.VISIBLE : View.GONE);
            avatar.setVisibility(tTypedArray.getBoolean(R.styleable.ItemView2_IsShowAvatar, false) ? View.VISIBLE : View.GONE);
            if(!aBoolean){
                ((RelativeLayout.LayoutParams) value.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            }
            check.setVisibility(tTypedArray.getBoolean(R.styleable.ItemView2_IsShowCheck, false) ? View.VISIBLE : View.GONE);
        } finally {
            if (tTypedArray != null)
                tTypedArray.recycle();
        }
    }


    public ItemView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setValue(String text) {
        value.setText(text);
    }

    public ImageView getAvatarView() {
        return avatar;
    }

    public void setChecked(boolean b) {
        check.setVisibility(b?VISIBLE:INVISIBLE);
    }
}
