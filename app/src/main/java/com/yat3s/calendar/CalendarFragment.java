package com.yat3s.calendar;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yat3s.calendar.calendar.CalendarView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
    CalendarView mCalendarView;

    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    @BindView(R.id.header_tv)
    TextView mHeader;

    @BindView(R.id.fab)
    FloatingActionButton mFab;

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
                mCalendarView.updatedCurrentSelectedItem(firstPosition);
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
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mCalendarView.close();
            }
        });

        mCalendarView.setCalendarDataSource(generateCalendarDateList());
        mCalendarView.updatedCurrentSelectedItem(0);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarView.open();
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

    private List<Day> generateCalendarDateList() {
        List<Day> days = new ArrayList<>();
        GregorianCalendar calendar = new GregorianCalendar();
        int year = calendar.get(Calendar.YEAR);
        while (year == calendar.get(Calendar.YEAR) || year == calendar.get(Calendar.YEAR) - 1) {
            days.add(new Day(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }

        return days;
    }
}
