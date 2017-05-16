package com.yat3s.calendar.other;

import android.os.Bundle;

import com.yat3s.calendar.BaseFragment;
import com.yat3s.calendar.R;

/**
 * Created by Yat3s on 16/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
public class InboxFragment extends BaseFragment {

    public static InboxFragment newInstance() {

        Bundle args = new Bundle();

        InboxFragment fragment = new InboxFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int getContentLayoutResId() {
        return R.layout.fragment_inbox;
    }

    @Override
    protected void initialize() {
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && null != getActivity()) {
            getActivity().setTitle(getString(R.string.tab_name_inbox));
        }
    }
}
