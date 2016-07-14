package com.kouchen.mininetlive.ui.widget;

import android.app.Dialog;
import android.content.Context;

/**
 * Created by cainli on 16/6/30.
 */
public class PayDialog extends Dialog {
    public PayDialog(Context context) {
        super(context);
    }

    public PayDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected PayDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
