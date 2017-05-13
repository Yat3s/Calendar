package com.yat3s.calendar;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Yat3s on 13/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
public class CalendarFragment extends BaseFragment {
    private static final String TAG = "CalendarFragment";

    @BindView(R.id.calendar_view)
    RecyclerView mCalendarView;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.header_tv)
    TextView mHeader;

    private LinearLayoutManager mLinearLayoutManager;
    private int mTotalDy = 0;
    private int mItemDy = 0;
    private int mLastFirstPosition;

    public static CalendarFragment newInstance() {

        Bundle args = new Bundle();

        CalendarFragment fragment = new CalendarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentLayoutResId() {
        return R.layout.fragment_calendar;
    }

    @Override
    protected void initialize() {
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        final MusicAdapter musicAdapter = new MusicAdapter(getContext(), generateMockData());
        mRecyclerView.setAdapter(musicAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastPosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                int firstPosition = mLinearLayoutManager.findFirstVisibleItemPosition();
                mHeader.setText("Section" + musicAdapter.getDataSource().get(firstPosition));
                mTotalDy += dy;
                if (firstPosition != mLastFirstPosition) {
                    mItemDy = 0;
                    mLastFirstPosition = firstPosition;
                } else {
                    mItemDy += dy;
                }
                moveHeader(mItemDy);
                Log.d(TAG, "mTotalDy: " + mTotalDy);
                Log.d(TAG, "dy: " + dy);
                Log.d(TAG, "lastPosition: " + lastPosition);
                Log.d(TAG, "computeScrollVectorForPosition: " + mLinearLayoutManager.computeScrollVectorForPosition
                        (lastPosition - 1).toString());
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }
        });
    }


    private void moveHeader(int dy) {
        if (dy < 0) {
            return;
        }
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mHeader.getLayoutParams();
        layoutParams.setMargins(0, -dy, 0, 0);
        mHeader.setLayoutParams(layoutParams);
    }

    private List<String> generateMockData() {
        int quantity = 100;
        List<String> mockData = new ArrayList<>();
        for (int idx = 0; idx < quantity; idx++) {
            mockData.add(String.valueOf(idx));
        }
        return mockData;
    }
}
