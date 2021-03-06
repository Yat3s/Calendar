package com.yat3s.calendar.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Yat3s on 15/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
public class SquaredTextView extends TextView {
    public SquaredTextView(Context context) {
        this(context, null);
    }

    public SquaredTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquaredTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // Make this view as square.
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }
}
