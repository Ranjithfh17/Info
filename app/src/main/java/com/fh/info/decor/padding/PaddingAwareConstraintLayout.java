package com.fh.info.decor.padding;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.fh.info.utils.StatusBarHeight;

public class PaddingAwareConstraintLayout extends ConstraintLayout {

    public PaddingAwareConstraintLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public PaddingAwareConstraintLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PaddingAwareConstraintLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

  private void init(){
       setPadding(getPaddingLeft(),
               StatusBarHeight.INSTANCE.getStatusBarHeight(getResources()) + getPaddingTop(),
               getPaddingRight(),
               getPaddingBottom());
  }
}
