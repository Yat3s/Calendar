package com.yat3s.calendar;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import com.yat3s.calendar.widget.BaseAdapter;
import com.yat3s.calendar.widget.BaseViewHolder;

import java.util.List;

/**
 * Created by Yat3s on 14/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
public class CalendarAdapter extends BaseAdapter<Day> {
    private int mLastSelectedPosition;

    public CalendarAdapter(Context context, List<Day> data) {
        super(context, data);
    }

    @Override
    protected int getItemViewLayoutId(int position, Day data) {
        return R.layout.item_calendar;
    }

    @Override
    protected void bindDataToItemView(final BaseViewHolder holder, final Day day, final int position) {

        // Configure calendar data.
        holder.setText(R.id.highlight_day_tv, String.valueOf(day.dayInMonth))
                .setText(R.id.day_tv, String.valueOf(day.dayInMonth))
                .setText(R.id.month_tv, day.monthAbbr)
                .setText(R.id.year_tv, String.valueOf(day.year))
                .setVisible(R.id.month_tv, day.isFirstDayInMonth)
                .setVisible(R.id.year_tv, !day.isThisYear)
                .setBackgroundResource(R.id.root_layout,
                        (day.month & 1) > 0 ? R.drawable.selector_common : R.drawable.selector_common_grey);

        // Is today
        ((TextView) holder.getView(R.id.day_tv)).setTypeface(null, day.isToday ? Typeface.BOLD : Typeface.NORMAL);
        holder.setTextColorRes(R.id.day_tv, day.isToday ? R.color.colorPrimary : R.color.textColorGrey);

        updateItemSelectableUI(day, holder);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day.isSelected = true;
                updateItemSelectableUI(day, holder);
                updateLastSelectedItem(position);
            }
        });
    }


    private void updateItemSelectableUI(Day day, BaseViewHolder holder) {
        holder.setVisible(R.id.highlight_day_tv, day.isSelected)
                .setVisible(R.id.event_badge_view, !day.isSelected && !day.isFirstDayInMonth && day.hasEvent)
                .setVisible(R.id.date_layout, !day.isSelected);
    }

    public void updateLastSelectedItem(int selectedPosition) {
        if (mLastSelectedPosition != selectedPosition) {
            getDataSource().get(mLastSelectedPosition).isSelected = false;
            notifyItemChanged(mLastSelectedPosition);
            mLastSelectedPosition = selectedPosition;
        }
    }
}
