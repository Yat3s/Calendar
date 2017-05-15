package com.yat3s.calendar;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yat3s.calendar.widget.BaseAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Yat3s on 14/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
public class CalendarView extends FrameLayout {
    private static final int CALENDAR_WEEK_SPAN = 7;
    private static final int WEEK_INDICATOR_TEXT_SIZE = 12; // sp

    @BindView(R.id.week_indicator_layout)
    LinearLayout mWeekIndicatorLayout;
    @BindView(R.id.calendar_rv)
    RecyclerView mCalendarRv;

    private CalendarAdapter mCalendarAdapter;

    public CalendarView(Context context) {
        this(context, null);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_calendar, this, true);
        ButterKnife.bind(this);

        // Configure calendar recycler view.
        mCalendarAdapter = new CalendarAdapter(getContext());
        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(getResources().getDrawable(R.drawable.shape_divider));
        mCalendarRv.setLayoutManager(new GridLayoutManager(getContext(), CALENDAR_WEEK_SPAN));
        mCalendarRv.addItemDecoration(divider);
        mCalendarRv.setAdapter(mCalendarAdapter);

        mCalendarAdapter.setOnItemClickListener(new BaseAdapter.OnItemClickListener<Day>() {
            @Override
            public void onClick(View view, Day day, int position) {
                // TODO: 15/05/2017
            }
        });

        // Configure week indicator.
        String[] weekAbbr = {"S", "M", "T", "W", "T", "F", "S"};
        mWeekIndicatorLayout.removeAllViews(); // Keep mWeekIndicatorLayout is empty.
        for (int idx = 0; idx < weekAbbr.length; idx++) {
            TextView weekIndicator = new TextView(getContext());

            // Make indicator divide equally in container.
            LinearLayout.LayoutParams indicatorLayoutParams =
                    new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
            indicatorLayoutParams.weight = 1;
            weekIndicator.setLayoutParams(indicatorLayoutParams);
            weekIndicator.setGravity(Gravity.CENTER);

            weekIndicator.setText(weekAbbr[idx]);
            weekIndicator.setTextSize(TypedValue.COMPLEX_UNIT_SP, WEEK_INDICATOR_TEXT_SIZE);
            weekIndicator.setTypeface(null, Typeface.BOLD);

            // Indicator color, Saturday and Sunday is GREY, other WHITE.
            weekIndicator.setTextColor((idx == 0 || idx == 6)
                    ? getResources().getColor(R.color.md_grey_400)
                    : getResources().getColor(R.color.md_white_1000));
            mWeekIndicatorLayout.addView(weekIndicator);
        }
    }

    public void setCalendarDataSource(List<Day> dataSource) {
        mCalendarAdapter.addFirstDataSet(dataSource);
    }
}
