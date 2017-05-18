package com.yat3s.calendar.common.widget;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.view.View;

/**
 * Created by Yat3s on 14/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
public class AnimateViewWrapper {
    private static final String PROPERTY_HEIGHT = "customHeight";
    private static final int ANIMATION_DURATION = 300;
    private View mView;

    public AnimateViewWrapper(View view) {
        mView = view;
    }

    /**
     * For PropertyAnimator can animate property, should impl setX and getX.
     * {@see} {@link android.animation.ValueAnimator}
     *
     * @return
     */
    public int getCustomHeight() {
        return mView.getLayoutParams().height;
    }

    public int getCustomWidth() {
        return mView.getLayoutParams().width;
    }

    public void setCustomWidth(int width) {
        mView.getLayoutParams().width = width;
        mView.requestLayout();
    }

    public void setCustomHeight(int height) {
        mView.getLayoutParams().height = height;
        mView.requestLayout();
    }

    /**
     * Animate height with values.
     * {@link #animateHeight(int...)} ;
     *
     * @param values
     */
    public void animateHeight(int... values) {
        animateHeight(ANIMATION_DURATION, null, values);
    }

    /**
     * Animate height with values.
     *
     * @param values
     */
    public void animateHeight(int duration, Animator.AnimatorListener animatorListener, int... values) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(this, PROPERTY_HEIGHT, values);
        if (null != animatorListener) {
            objectAnimator.addListener(animatorListener);
        }
        objectAnimator.setDuration(duration);
        objectAnimator.start();
    }
}
