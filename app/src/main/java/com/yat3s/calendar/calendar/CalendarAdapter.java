package com.yat3s.calendar.calendar;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.yat3s.calendar.R;
import com.yat3s.calendar.common.widget.BaseAdapter;
import com.yat3s.calendar.common.widget.BaseViewHolder;
import com.yat3s.calendar.data.model.Day;

/**
 * Created by Yat3s on 14/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 * <p>
 * The adapter of calendar list including process selectable item
 * {@see} {@link #updateCurrentSelectedItem}
 * {@link #setOnItemSelectedListener(OnItemSelectedListener)}
 */
public class CalendarAdapter extends BaseAdapter<Day> {
    private static final String TAG = "CalendarAdapter";
    public int mLastSelectedPosition = -1;

    private OnItemSelectedListener<Day> mOnItemSelectedListener;

    // Hold for get current show position ViewHolder.
    // Ref: updateCurrentSelectedItem();
    private RecyclerView mRecyclerView;

    public CalendarAdapter(Context context) {
        super(context);
    }

    @Override
    protected int getItemViewLayoutId(int position, Day data) {
        return R.layout.item_calendar;
    }

    @Override
    protected void bindDataToItemView(final BaseViewHolder holder, final Day day, final int position) {

        // Configure calendar common data.
        holder.setText(R.id.highlight_day_tv, String.valueOf(day.dayOfMonth))
                .setText(R.id.day_tv, String.valueOf(day.dayOfMonth))
                .setText(R.id.month_tv, day.monthAbbr)
                .setText(R.id.year_tv, String.valueOf(day.year))
                .setVisible(R.id.month_tv, day.isFirstDayInMonth)
                .setVisible(R.id.year_tv, !day.isThisYear && day.isFirstDayInMonth)
                .setBackgroundResource(R.id.root_layout,
                        day.isToday ? R.color.blackSqueeze :
                                (day.month & 1) > 0 ? R.drawable.selector_common : R.drawable.selector_common_grey);

        // Is today
        ((TextView) holder.getView(R.id.day_tv)).setTypeface(null, day.isToday ? Typeface.BOLD : Typeface.NORMAL);
        holder.setTextColorRes(R.id.day_tv, day.isToday ? R.color.colorPrimary : R.color.textColorGrey);

        updateItemSelectableUI(day, holder);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cancel last selected item.
                if (mLastSelectedPosition != position) {
                    getDataSource().get(mLastSelectedPosition).isSelected = false;
                    notifyItemChanged(mLastSelectedPosition);
                    mLastSelectedPosition = position;
                }

                // Highlight item immediately.
                day.isSelected = true;
                updateItemSelectableUI(day, holder);

                if (null != mOnItemSelectedListener) {
                    mOnItemSelectedListener.onSelected(day, position);
                }
            }
        });
    }

    /**
     * Updated item UI when day.selected has changed.
     *
     * @param day    {@link Day}
     * @param holder {@link BaseViewHolder}
     */
    private void updateItemSelectableUI(Day day, BaseViewHolder holder) {
        if (null == holder) {
            return;
        }
        holder.setVisible(R.id.highlight_day_tv, day.isSelected)
                .setVisible(R.id.event_badge_view, !day.isSelected && !day.isFirstDayInMonth && day.hasEvent())
                .setVisible(R.id.date_layout, !day.isSelected);
    }

    /**
     * Updated current selected item, it contain check current item and
     * uncheck last item.
     * <p>
     * {@see} {@link RecyclerView#findViewHolderForLayoutPosition(int)} will find view holder and
     * update status immediately.
     *
     * @param selectedPosition
     */
    public void updateCurrentSelectedItem(int selectedPosition) {
        if (mLastSelectedPosition != selectedPosition) {

            // Check current selected item.
            Day day = getDataSource().get(selectedPosition);
            day.isSelected = true;
            if (null != mRecyclerView) {
                // If find view holder from recycler pool, update status immediately.
                BaseViewHolder holder = (BaseViewHolder) mRecyclerView.findViewHolderForLayoutPosition(selectedPosition);
                updateItemSelectableUI(day, holder);
            } else {
                notifyItemChanged(selectedPosition);
            }

            // Uncheck last selected item.
            if (mLastSelectedPosition != -1) {
                Day lastDay = getDataSource().get(mLastSelectedPosition);
                lastDay.isSelected = false;
                if (null != mRecyclerView) {
                    BaseViewHolder lastHolder = (BaseViewHolder) mRecyclerView.
                            findViewHolderForLayoutPosition(mLastSelectedPosition);
                    updateItemSelectableUI(lastDay, lastHolder);
                } else {
                    notifyItemChanged(mLastSelectedPosition);
                }

            }
            mLastSelectedPosition = selectedPosition;
        }
    }

    /**
     * Get recycler view when this adapter attach to recycler view.
     *
     * @param recyclerView
     */
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        super.onAttachedToRecyclerView(recyclerView);
    }

    /**
     * Register item select listener.
     * NOTE: it only callback from user click item.
     *
     * @param clickListener
     */
    public void setOnItemSelectedListener(OnItemSelectedListener<Day> clickListener) {
        mOnItemSelectedListener = clickListener;
    }

    public interface OnItemSelectedListener<T> {
        void onSelected(T t, int position);
    }
}
