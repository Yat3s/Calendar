package com.yat3s.calendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by Yat3s on 13/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
public abstract class BaseFragment extends Fragment {
    protected abstract int getContentLayoutResId();

    protected abstract void initialization();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getContentLayoutResId(), container, false);
        ButterKnife.bind(this, rootView);
        initialization();
        return rootView;
    }
}
