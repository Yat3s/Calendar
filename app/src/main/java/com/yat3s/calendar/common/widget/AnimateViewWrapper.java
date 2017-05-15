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
    private View mView;

    public AnimateViewWrapper(View view) {
        mView = view;
    }

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

    public void animateHeight(int... values) {
        animateHeight(300, null, values);
    }

    public void animateHeight(int duration, Animator.AnimatorListener animatorListener, int... values) {
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(this, "customHeight", values);
        if (null != animatorListener) {
            objectAnimator.addListener(animatorListener);
        }
        objectAnimator.setDuration(duration);
        objectAnimator.start();
    }

    public void setCustomHeight(int height) {
        mView.getLayoutParams().height = height;
        mView.requestLayout();
    }
}
