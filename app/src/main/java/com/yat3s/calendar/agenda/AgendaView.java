package com.yat3s.calendar.agenda;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yat3s.calendar.R;
import com.yat3s.calendar.common.util.MetricsUtil;
import com.yat3s.calendar.data.model.Day;
import com.yat3s.calendar.data.source.WeatherDataSource;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Yat3s on 15/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 * <p>
 * A custom view contain agenda list and some operation
 * {@link #setOnAgendaScrollListener(OnAgendaScrollListener)}
 * {@link #updateWeatherDataSource(WeatherDataSource)}
 */
public class AgendaView extends FrameLayout {
    private static final String TAG = "AgendaView";
    @BindView(R.id.agenda_rv)
    RecyclerView mAgendaRv;

    @BindView(R.id.header_tv)
    TextView mHeaderTv;

    @BindView(R.id.header_layout)
    LinearLayout mHeaderLayout;

    // The layout manager of agenda recycler view.
    private LinearLayoutManager mLinearLayoutManager;

    // The adapter of agenda recycler view.
    private AgendaAdapter mAgendaAdapter;

    private OnAgendaScrollListener mOnAgendaScrollListener;

    // The divider height in dp.
    private float mDividerHeight;

    // The last first visible item position.
    private int mLastFirstPosition;

    // Current item belong which month.
    private String mDisplayMonth;

    public AgendaView(Context context) {
        this(context, null);
    }

    public AgendaView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AgendaView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        initialize();
    }

    private void initialize() {
        LayoutInflater.from(getContext()).inflate(R.layout.view_agenda, this, true);
        ButterKnife.bind(this);

        configureAgendaRecyclerView();
    }

    /**
     * Configure agenda recycler view and register scroll listener to
     * process scroll operations.
     */
    private void configureAgendaRecyclerView() {
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mAgendaRv.setLayoutManager(mLinearLayoutManager);
        mAgendaAdapter = new AgendaAdapter(getContext());
        mAgendaRv.setAdapter(mAgendaAdapter);

        mDividerHeight = MetricsUtil.dp2px(getContext(), getResources().getDimension(R.dimen.divider_size));
        mAgendaRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int firstVisibleItemPosition = mLinearLayoutManager.findFirstVisibleItemPosition();

                // Update header location.
                int firstCompletelyVisibleItemPosition = mLinearLayoutManager.findFirstCompletelyVisibleItemPosition();
                View firstCompleteVisibleView = mLinearLayoutManager.findViewByPosition(firstCompletelyVisibleItemPosition);
                float firstCompleteVisibleViewY = 0.0f;
                if (null != firstCompleteVisibleView) {
                    firstCompleteVisibleViewY = firstCompleteVisibleView.getY();
                }
                translateHeader((int) (mHeaderLayout.getHeight() - firstCompleteVisibleViewY - mDividerHeight));

                // Trigger agenda listener.
                if (null != mOnAgendaScrollListener) {
                    mOnAgendaScrollListener.onScrolled(dy);
                }
                if (firstVisibleItemPosition != mLastFirstPosition) {
                    if (null != mOnAgendaScrollListener && dy != 0) {
                        mOnAgendaScrollListener.onFirstVisibleItemPositionChanged(firstVisibleItemPosition);
                    }
                    if (firstVisibleItemPosition > 0 &&
                            firstVisibleItemPosition < mAgendaAdapter.getDataSource().size()) {
                        Day firstVisibleDay = mAgendaAdapter.getDataSource().get(firstVisibleItemPosition);
                        updateHeaderText(firstVisibleDay);
                        if (!TextUtils.equals(firstVisibleDay.monthName, mDisplayMonth) && null != mOnAgendaScrollListener) {
                            mOnAgendaScrollListener.onDisplayMonthChanged(firstVisibleDay.monthName);
                            mDisplayMonth = firstVisibleDay.monthName;
                        }
                    }
                    mLastFirstPosition = firstVisibleItemPosition;
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (null != mOnAgendaScrollListener) {
                    mOnAgendaScrollListener.onScrollStateChanged(newState);
                }
            }
        });
    }

    /**
     * Set agenda view data source and make today agenda as default first.
     *
     * @param dataSource {@link Day}
     */
    public void setAgendaDataSource(List<Day> dataSource) {
        mAgendaAdapter.addFirstDataSet(dataSource);

        // Default show today agenda.
        if (null != dataSource && dataSource.size() > 0) {
            for (int idx = 0; idx < dataSource.size(); idx++) {
                if (dataSource.get(idx).isToday) {
                    updateHeaderText(dataSource.get(idx));
                    scrollToPosition(idx);
                    break;
                }
            }
        }
    }

    /**
     * Update weather data source and notify adapter.
     * {@link AgendaAdapter#updateWeatherDataSource(WeatherDataSource)}
     *
     * @param weatherDataSource
     */
    public void updateWeatherDataSource(@NonNull WeatherDataSource weatherDataSource) {
        mAgendaAdapter.updateWeatherDataSource(weatherDataSource);
    }

    /**
     * Scroll to target position
     * {@see} {@link LinearLayoutManager#scrollToPosition(int)}
     *
     * @param position
     */
    public void scrollToPosition(int position) {
        // Some tricks for LayoutManager cannot scroll to target position shown in screen.
        mLinearLayoutManager.scrollToPositionWithOffset(position, 0);
    }

    /**
     * Agenda list scroll listener.
     *
     * @param scrollListener {@link OnAgendaScrollListener}
     */
    public void setOnAgendaScrollListener(@Nullable OnAgendaScrollListener scrollListener) {
        mOnAgendaScrollListener = scrollListener;
    }

    /**
     * Update header text
     *
     * @param day
     */
    private void updateHeaderText(Day day) {
        mHeaderTv.setText(day.getDateSectionString());
        mHeaderTv.setTextColor(getResources().getColor(day.isToday ? R.color.colorPrimary : R.color.textColorGrey));
        mHeaderTv.setBackgroundResource(day.isToday ? R.color.blackSqueeze : R.color.md_white_1000);
    }

    /**
     * Translate section header while agenda scrolling.
     *
     * @param dy the distance of section header translation.
     */
    private void translateHeader(int dy) {
        // Just section header
        if (dy >= mHeaderLayout.getHeight() || dy < 0) {
            dy = 0;
        }

        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mHeaderLayout.getLayoutParams();
        layoutParams.setMargins(0, -dy, 0, 0);
        mHeaderLayout.setLayoutParams(layoutParams);
    }

    /**
     * onFirstVisibleItemPositionChanged(int):
     * Triggering while first visible item position had changed.
     * {@See} {@link LinearLayoutManager#findFirstVisibleItemPosition()}
     * <p>
     * onScrollStateChanged(int):
     * Triggering while scroll state changed.
     * {@See} {@link android.support.v7.widget.RecyclerView.OnScrollListener#onScrollStateChanged(int)}
     * <p>
     * onDisplayMonthChanged(String):
     * The title show on activity when select calendar tab
     */
    public interface OnAgendaScrollListener {

        void onFirstVisibleItemPositionChanged(int position);

        void onScrolled(int dy);

        void onScrollStateChanged(int newState);

        void onDisplayMonthChanged(String month);
    }
}
