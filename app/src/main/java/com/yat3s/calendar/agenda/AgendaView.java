package com.yat3s.calendar.agenda;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yat3s.calendar.R;
import com.yat3s.calendar.data.model.Day;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Yat3s on 15/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
public class AgendaView extends FrameLayout {
    private static final String TAG = "AgendaView";
    @BindView(R.id.agenda_rv)
    RecyclerView mAgendaRv;

    @BindView(R.id.header_tv)
    TextView mHeaderTv;

    private LinearLayoutManager mLinearLayoutManager;

    private AgendaAdapter mAgendaAdapter;

    private OnAgendaScrollListener mOnAgendaScrollListener;

    private int mTotalDy = 0;

    private int mItemDy = 0;

    private int mLastFirstPosition;

    private String mDisplayMonth;

    public AgendaView(Context context) {
        this(context, null);
    }

    public AgendaView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AgendaView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        initialization();
    }

    private void initialization() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_agenda, this, true);
        ButterKnife.bind(this);

        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mAgendaRv.setLayoutManager(mLinearLayoutManager);
        mAgendaAdapter = new AgendaAdapter(getContext());
        mAgendaRv.setAdapter(mAgendaAdapter);

        mAgendaRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
                int firstVisibleItemPosition = mLinearLayoutManager.findFirstVisibleItemPosition();

                mTotalDy += dy;
                if (firstVisibleItemPosition != mLastFirstPosition) {
                    if (null != mOnAgendaScrollListener && dy != 0) {
                        mOnAgendaScrollListener.onFirstVisibleItemPositionChanged(firstVisibleItemPosition);
                    }
                    if (firstVisibleItemPosition < mAgendaAdapter.getDataSource().size()) {
                        Day firstVisibleDay = mAgendaAdapter.getDataSource().get(firstVisibleItemPosition);
                        setHeaderText(firstVisibleDay);
                        if (!TextUtils.equals(firstVisibleDay.monthName, mDisplayMonth) && null != mOnAgendaScrollListener) {
                            mOnAgendaScrollListener.onDisplayMonthChanged(firstVisibleDay.monthName);
                            mDisplayMonth = firstVisibleDay.monthName;
                        }
                    }
                    mItemDy = 0;
                    mLastFirstPosition = firstVisibleItemPosition;
                } else {
                    mItemDy += dy;
                }
                moveHeader(mItemDy);
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
     * Set agenda view data source
     *
     * @param dataSource
     */
    public void setAgendaDataSource(List<Day> dataSource) {
        mAgendaAdapter.addFirstDataSet(dataSource);

        // Default show today agenda.
        if (null != dataSource && dataSource.size() > 0) {
            for (int idx = 0; idx < dataSource.size(); idx++) {
                if (dataSource.get(idx).isToday) {
                    setHeaderText(dataSource.get(idx));
                    scrollToPosition(idx);
                    break;
                }
            }
        }
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
    public void setOnAgendaScrollListener(OnAgendaScrollListener scrollListener) {
        mOnAgendaScrollListener = scrollListener;
    }

    private void setHeaderText(Day day) {
        mHeaderTv.setText(day.getDateSectionString());
        mHeaderTv.setTextColor(getResources().getColor(day.isToday ? R.color.colorPrimary : R.color.textColorGrey));
    }

    private void moveHeader(int dy) {
        if (dy < 0) {
            return;
        }
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mHeaderTv.getLayoutParams();
        layoutParams.setMargins(0, -dy, 0, 0);
        mHeaderTv.setLayoutParams(layoutParams);
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

        void onScrollStateChanged(int newState);

        void onDisplayMonthChanged(String month);
    }
}
