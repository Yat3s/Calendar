package com.yat3s.calendar.agenda;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yat3s.calendar.data.model.Day;
import com.yat3s.calendar.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.ContentValues.TAG;

/**
 * Created by Yat3s on 15/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
public class AgendaView extends FrameLayout {
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
                    if (null != mOnAgendaScrollListener) {
                        mOnAgendaScrollListener.onFirstVisibleItemPositionChanged(firstVisibleItemPosition);
                    }
                    if (firstVisibleItemPosition < mAgendaAdapter.getDataSource().size()) {
                        setHeaderText(mAgendaAdapter.getDataSource().get(firstVisibleItemPosition));
                    }
                    mItemDy = 0;
                    mLastFirstPosition = firstVisibleItemPosition;
                } else {
                    mItemDy += dy;
                }
                moveHeader(mItemDy);
                Log.d(TAG, "mTotalDy: " + mTotalDy);
                Log.d(TAG, "dy: " + dy);
                Log.d(TAG, "lastPosition: " + lastVisibleItemPosition);
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

    public void setAgendaDataSource(List<Day> dataSource) {
        mAgendaAdapter.addFirstDataSet(dataSource);
        if (null != dataSource && dataSource.size() > 0) {
            setHeaderText(dataSource.get(0));
        }
    }

    /**
     * Agenda list scroll listener.
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
     */
    public interface OnAgendaScrollListener {

        void onFirstVisibleItemPositionChanged(int position);

        void onScrollStateChanged(int newState);
    }
}
