package com.fh.info.decor.ripple;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

public class RippleLinearLayout extends LinearLayout {

    public RippleLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setBackgroundColor(Color.TRANSPARENT);
        setBackground(RippleUtil.getRippleDrawable(getContext(), getBackground()));
    }

}
