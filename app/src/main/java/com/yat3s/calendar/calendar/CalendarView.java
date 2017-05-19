package com.yat3s.calendar.calendar;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yat3s.calendar.R;
import com.yat3s.calendar.common.util.MetricsUtil;
import com.yat3s.calendar.common.widget.AnimateViewWrapper;
import com.yat3s.calendar.data.model.Day;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Yat3s on 14/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
public class CalendarView extends FrameLayout {
    private static final String TAG = "CalendarView";
    private static final int CALENDAR_WEEK_SPAN = 7;

    // Calendar view row count show when expanded.
    private static final int CALENDAR_EXPANSION_ROW = 5;

    // Calendar view row count show when collapse.
    private static final int CALENDAR_FOLD_ROW = 2;

    // Calendar view week indicator text size in sp.
    private static final int WEEK_INDICATOR_TEXT_SIZE = 12; // sp

    @BindView(R.id.week_indicator_layout)
    LinearLayout mWeekIndicatorLayout;

    @BindView(R.id.calendar_rv)
    RecyclerView mCalendarRv;

    private CalendarAdapter mCalendarAdapter;

    // Mark calendar view expand status.
    private boolean isExpand;

    private int mExpandCalenderRecyclerViewHeight, mNarrowCalenderRecyclerViewHeight;

    // Calendar recycler view scrolled distance in Y while scroll state changed..
    private int mDy;

    // Calendar recycler view total scrolled distance in Y.
    private int mTotalDy;

    // Calendar recycler view first & last visible item position.
    private int mFirstVisibleItemPosition, mLastVisibleItemPosition;

    private int mCurrentScrollState = RecyclerView.SCROLL_STATE_IDLE;

    // Calendar recycler view layout manager, contain item view...
    private GridLayoutManager mGridLayoutManager;

    public CalendarView(Context context) {
        this(context, null);
    }

    public CalendarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        initialization();
    }

    private void initialization() {
        // Inflate calendar layout and let it attach to FrameLayout.
        LayoutInflater.from(getContext()).inflate(R.layout.view_calendar, this, true);
        ButterKnife.bind(this);

        // Initial calendar view height.
        initialCalendarViewHeight();

        // Configure calendar recycler view.
        configureCalendarRecyclerView();

        // Configure week indicator.
        configureWeekIndicator();
    }

    /**
     * Initialize calendar view height, make height as {@link #CALENDAR_FOLD_ROW}
     */
    private void initialCalendarViewHeight() {
        final int dividerHeight = (int) getResources().getDimension(R.dimen.divider_size);
        final int calenderItemSize = MetricsUtil.getScreenWidth(getContext()) / CALENDAR_WEEK_SPAN + dividerHeight;
        mExpandCalenderRecyclerViewHeight = calenderItemSize * CALENDAR_EXPANSION_ROW;
        mNarrowCalenderRecyclerViewHeight = calenderItemSize * CALENDAR_FOLD_ROW;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mCalendarRv.getLayoutParams();
        layoutParams.height = mNarrowCalenderRecyclerViewHeight;
        mCalendarRv.setLayoutParams(layoutParams);
    }

    /**
     * Configure recycler view and add scroll listener to
     * process some events or operations.
     */
    private void configureCalendarRecyclerView() {
        mCalendarAdapter = new CalendarAdapter(getContext());
        DividerItemDecoration divider = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(getResources().getDrawable(R.drawable.shape_divider));
        mGridLayoutManager = new GridLayoutManager(getContext(), CALENDAR_WEEK_SPAN);
        mCalendarRv.setLayoutManager(mGridLayoutManager);
        mCalendarRv.addItemDecoration(divider);
        mCalendarRv.setAdapter(mCalendarAdapter);
        ((SimpleItemAnimator) mCalendarRv.getItemAnimator()).setSupportsChangeAnimations(false);

        mCalendarRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mTotalDy += dy;
                mDy = dy;

                mLastVisibleItemPosition = mGridLayoutManager.findLastVisibleItemPosition();
                mFirstVisibleItemPosition = mGridLayoutManager.findFirstVisibleItemPosition();
                if (mCurrentScrollState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    expand();
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mCurrentScrollState = newState;
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        // TODO: 15/05/2017 BUG IN scroll to adaptive position
                        int scrollToAdaptivePosition = mFirstVisibleItemPosition;
                        if (scrollToAdaptivePosition >= 0
                                && scrollToAdaptivePosition < mCalendarAdapter.getDataSource().size()) {
                            mCalendarRv.smoothScrollToPosition(mFirstVisibleItemPosition);
                        }

                        break;
                }
            }
        });
    }

    /**
     * Configure calendar top week indicator layout.
     */
    private void configureWeekIndicator() {
        final String[] weekAbbr = {"S", "M", "T", "W", "T", "F", "S"};
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

    /**
     * Fill calendar view data source and show.
     *
     * @param dataSource
     */
    public void setCalendarDataSource(List<Day> dataSource) {
        mCalendarAdapter.addFirstDataSet(dataSource);

        // Default show today agenda.
        if (null != dataSource && dataSource.size() > 0) {
            for (int idx = 0; idx < dataSource.size(); idx++) {
                if (dataSource.get(idx).isToday) {
                    updatedCurrentSelectedItem(idx);
                    break;
                }
            }
        }
    }

    /**
     * Updated calendar view current select item.
     * It will scroll down while position > mLastVisibleItemPosition
     * and scroll up while position < mLastVisibleItemPosition
     * <p>
     * {@see} {@link CalendarAdapter#updateCurrentSelectedItem(int)}
     *
     * @param position
     */
    public void updatedCurrentSelectedItem(int position) {
        if (mCalendarAdapter.mLastSelectedPosition == position) {
            return;
        }
        if (position > mLastVisibleItemPosition) {
            mGridLayoutManager.scrollToPosition(position);
        }
        if (position < mFirstVisibleItemPosition) {
            mGridLayoutManager.scrollToPosition(position);
        }
        mCalendarAdapter.updateCurrentSelectedItem(position);
    }

    /**
     * Set calendar item click listener.
     *
     * @param listener
     */
    public void setOnItemSelectedListener(CalendarAdapter.OnItemSelectedListener<Day> listener) {
        mCalendarAdapter.setOnItemSelectedListener(listener);
    }

    /**
     * Expand calender view height to {@link #CALENDAR_EXPANSION_ROW}
     */
    public void expand() {
        if (!isExpand) {
            final AnimateViewWrapper animateViewWrapper = new AnimateViewWrapper(mCalendarRv);
            animateViewWrapper.animateHeight(mExpandCalenderRecyclerViewHeight);
            isExpand = true;
        }
    }

    /**
     * Collapse calender view height to {@link #CALENDAR_FOLD_ROW}
     */
    public void collapse() {
        if (isExpand) {
            final AnimateViewWrapper animateViewWrapper = new AnimateViewWrapper(mCalendarRv);
            animateViewWrapper.animateHeight(mNarrowCalenderRecyclerViewHeight);
            isExpand = false;
        }
    }
}
