package com.kouchen.mininetlive.ui.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kouchen.mininetlive.R;

/**
 * Created by cainli on 16/7/17.
 */
public class GenderChooseView extends LinearLayout implements View.OnClickListener {

    private ImageView maleImageView;//ic_male
    private ImageView femaleImageView; //ic_female

    private int gender = 0;

    public GenderChooseView(Context context) {
        this(context, null);
    }

    public GenderChooseView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GenderChooseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setOrientation(HORIZONTAL);
        setWeightSum(2);
        LinearLayout.LayoutParams layoutParams;
        maleImageView = new ImageView(context);
        layoutParams = new LayoutParams(-2, -2);
        layoutParams.weight = 1;
        addView(maleImageView, layoutParams);
        maleImageView.setOnClickListener(this);

        femaleImageView = new ImageView(context);
        layoutParams = new LayoutParams(-2, -2);
        layoutParams.weight = 1;
        addView(femaleImageView, layoutParams);
        femaleImageView.setOnClickListener(this);

        setGender(true);
    }

    public void setGender(boolean isFemale) {
        if (isFemale) {
            gender= 0;
            femaleImageView.setImageResource(R.drawable.ic_female);
            maleImageView.setImageResource(R.drawable.ic_male_default);
        } else {
            gender = 1;
            femaleImageView.setImageResource(R.drawable.ic_female_default);
            maleImageView.setImageResource(R.drawable.ic_male);
        }
    }


    @Override
    public void onClick(View view) {
        if (view == femaleImageView) {
            setGender(true);
        } else if (view == maleImageView) {
            setGender(false);
        }
    }

    public int getValue() {
        return gender;
    }
}
