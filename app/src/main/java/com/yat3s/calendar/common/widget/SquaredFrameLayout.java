package com.yat3s.calendar.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

/**
 * Created by Yat3s on 14/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
public class SquaredFrameLayout extends FrameLayout {
    public SquaredFrameLayout(Context context) {
        this(context, null);
    }

    public SquaredFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquaredFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }
}
