package com.yat3s.calendar.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.view.View;

import com.yat3s.calendar.R;

/**
 * Created by Yat3s on 17/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
public class CircleView extends View {
    private Paint mPaint;
    private int mColorResId;

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.CircleView);
        mColorResId = typeArray.getResourceId(R.styleable.CircleView_backgroundColor, R.color.colorPrimary);
        typeArray.recycle();

        initialize();
    }

    private void initialize() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(getResources().getColor(mColorResId));
    }

    /**
     * Updated paint color.
     *
     * @param color
     */
    public void setColor(@ColorInt int color) {
        mPaint.setColor(color);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, Math.min(getWidth(), getHeight()) / 2, mPaint);
    }
}
