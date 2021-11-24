package com.fh.info.decor.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.fh.info.R;
import com.fh.info.decor.ripple.RippleUtil;

public class RippleTextView extends AppCompatTextView {


    public RippleTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RippleTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        setBackgroundColor(Color.TRANSPARENT);
        setBackground(RippleUtil.getRippleDrawable(getContext(),getBackground()));

    }
}
