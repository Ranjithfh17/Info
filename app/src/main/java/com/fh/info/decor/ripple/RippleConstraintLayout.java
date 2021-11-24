package com.fh.info.decor.ripple;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.fh.info.R;

public class RippleConstraintLayout extends ConstraintLayout {
    public RippleConstraintLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public RippleConstraintLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RippleConstraintLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setBackgroundColor(Color.TRANSPARENT);
        setBackground(RippleUtil.getRippleDrawable(getContext(), getBackground()));
    }

    private void setDefaultBackground(boolean selected) {
        if (selected) {
            setBackgroundTintList(null);
            setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.green)));
        } else {
            setBackground(null);
            setBackground(RippleUtil.getRippleDrawable(getContext(), getBackground()));

        }


    }
}
