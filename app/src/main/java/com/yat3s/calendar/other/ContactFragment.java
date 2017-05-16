package com.yat3s.calendar.other;

import android.os.Bundle;

import com.yat3s.calendar.BaseFragment;
import com.yat3s.calendar.R;

/**
 * Created by Yat3s on 16/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
public class ContactFragment extends BaseFragment {
    private static final String TAG = "ContactFragment";

    public static ContactFragment newInstance() {

        Bundle args = new Bundle();

        ContactFragment fragment = new ContactFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentLayoutResId() {
        return R.layout.fragment_contact;
    }

    @Override
    protected void initialize() {
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && null != getActivity()) {
            getActivity().setTitle(getString(R.string.tab_name_contact));
        }
    }
}
