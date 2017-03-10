package com.michal_stasinski.distrada.menu;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by win8 on 04.01.2017.
 */

public class CustomTextView extends TextView {

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/0248EU27.ttf"));
       // this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/arial.ttf"));
    }
}
