package com.fh.info.decor.ripple;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;

import androidx.core.content.ContextCompat;

import com.fh.info.R;

import java.util.Arrays;

public class RippleUtil {

    public static RippleDrawable getRippleDrawable(Context context, Drawable drawable){
         float[] innerRadius=new float[8];
         float[] outerRadius =new float[8];

         Arrays.fill(innerRadius,20);
         Arrays.fill(outerRadius,20);

         RoundRectShape roundRectShape=new RoundRectShape(outerRadius,null,innerRadius);
         ShapeDrawable mask=new ShapeDrawable(roundRectShape);
         ColorStateList stateList = ColorStateList.valueOf(ContextCompat.getColor(context,R.color.green));



        RippleDrawable rippleDrawable=new RippleDrawable(stateList,drawable,mask);
        rippleDrawable.setAlpha(70);
        return rippleDrawable;

    }
}
