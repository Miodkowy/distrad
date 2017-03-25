package com.michal_stasinski.distrada.Menu;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by win8 on 04.01.2017.
 */

public class CustomTextView extends android.support.v7.widget.AppCompatTextView {

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
       // this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/0248EU27.ttf"));
        this.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/arial.ttf"));
    }
}
