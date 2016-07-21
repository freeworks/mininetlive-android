package com.kouchen.mininetlive.ui.widget;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kouchen.mininetlive.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by cainli on 16/7/17.
 */
public class PanelView extends RelativeLayout {

    private View contentView;
    private int contentViewHeight;
    private Drawable backgroud;

    private boolean isShowing;

    public PanelView(Context context) {
        this(context, null);

    }

    public PanelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PanelView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        backgroud = new ColorDrawable(Color.BLACK);
        backgroud.setAlpha(0);
        setBackgroundDrawable(backgroud);
    }

    public void addContentView(View view) {
        contentView = view;
        ViewParent parent = view.getParent();
        if (parent != null) {
            ViewGroup vg = (ViewGroup) parent;
            vg.removeView(view);
        }
        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(ALIGN_PARENT_BOTTOM);
        addView(view, layoutParams);
        contentView.measure(MeasureSpec.makeMeasureSpec(getWidth(), MeasureSpec.UNSPECIFIED), MeasureSpec.makeMeasureSpec(getHeight(), MeasureSpec.UNSPECIFIED));
        contentViewHeight = contentView.getHeight();
    }

    public void show() {
        isShowing = true;
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(backgroud, PropertyValuesHolder.ofInt("alpha", 0, 100));
        animator.setTarget(backgroud);
        ObjectAnimator animator2 = ObjectAnimator.ofPropertyValuesHolder(contentView, PropertyValuesHolder.ofFloat("y", contentView.getY() - contentViewHeight, contentView.getY()));
        animator.setTarget(contentView);
        AnimatorSet animationSet = new AnimatorSet();
        animationSet.playTogether(animator, animator2);
        animationSet.setDuration(200);
        animationSet.start();
    }

    public void hide() {
        isShowing = false;
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(backgroud, PropertyValuesHolder.ofInt("alpha", 100, 0));
        animator.setTarget(backgroud);
        ObjectAnimator animator2 = ObjectAnimator.ofPropertyValuesHolder(contentView, PropertyValuesHolder.ofFloat("y", getY() - contentViewHeight, getY()));
        animator.setTarget(contentView);
        AnimatorSet animationSet = new AnimatorSet();
        animationSet.playTogether(animator, animator2);
        animationSet.setDuration(200);
        animationSet.start();
    }

    public boolean isShowing() {
        return isShowing;
    }
}
