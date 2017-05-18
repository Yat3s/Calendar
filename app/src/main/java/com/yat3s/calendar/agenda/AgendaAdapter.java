package com.yat3s.calendar.agenda;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import com.yat3s.calendar.R;
import com.yat3s.calendar.common.util.CalendarHelper;
import com.yat3s.calendar.common.widget.BaseAdapter;
import com.yat3s.calendar.common.widget.BaseViewHolder;
import com.yat3s.calendar.common.widget.CircleView;
import com.yat3s.calendar.data.model.Day;
import com.yat3s.calendar.data.model.Event;

/**
 * Created by Yat3s on 15/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
public class AgendaAdapter extends BaseAdapter<Day> {
    private static final int TITLE_SIZE_HAS_EVENT = 14;
    private static final int TITLE_SIZE_NO_EVENT = 12;

    public AgendaAdapter(Context context) {
        super(context);
    }

    @Override
    protected void bindDataToItemView(BaseViewHolder holder, Day day, int position) {
        holder.setText(R.id.date_tv, day.getDateSectionString())
                .setTextColorRes(R.id.date_tv, day.isToday ? R.color.colorPrimary : R.color.textColorGrey)
                .setOnClickListener(R.id.event_layout, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
        TextView eventTitle = holder.getView(R.id.event_title_tv);

        if (day.hasEvent()) {
            // Just load first event for....mock.
            Event event = day.getEvents().get(0);

            // Set duration data.
            String startAt = event.allDay == 1 ? "ALL DAY" : CalendarHelper.getHour(event.eventStart);
            String duration = event.allDay == 1 ? "1d" : CalendarHelper.getInterval(event.eventStart, event.eventEnd);
            SpannableString durationSpannableString = new SpannableString(startAt + "\n" + duration);
            durationSpannableString.setSpan(new ForegroundColorSpan(Color.BLACK),
                    0, startAt.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            durationSpannableString.setSpan(new ForegroundColorSpan(Color.GRAY),
                    startAt.length(), (startAt + duration).length() + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

            // Set event display color.
            ((CircleView) holder.getView(R.id.display_color_view)).setColor(event.getDisplayColor());
            holder.setText(R.id.event_title_tv, event.title)
                    .setText(R.id.event_duration_tv, durationSpannableString)
                    .setTextColorRes(R.id.event_title_tv, R.color.md_black_1000)
                    .setVisible(R.id.display_color_view, true)
                    .setVisible(R.id.event_duration_tv, true);
            eventTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, TITLE_SIZE_HAS_EVENT);

            // Set location
            if (TextUtils.isEmpty(event.location)) {
                holder.setVisible(R.id.event_location_tv, false);
            } else {
                holder.setVisible(R.id.event_location_tv, true)
                        .setText(R.id.event_location_tv, mContext.getString(R.string.icon_location) + " " + event.location);
            }
        } else {
            holder.setText(R.id.event_title_tv, mContext.getString(R.string.no_event_title))
                    .setTextColorRes(R.id.event_title_tv, R.color.textColorGrey)
                    .setVisible(R.id.display_color_view, false)
                    .setVisible(R.id.event_duration_tv, false);
            eventTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, TITLE_SIZE_NO_EVENT);

        }
    }

    @Override
    protected int getItemViewLayoutId(int position, Day data) {
        return R.layout.item_agenda;
    }
}
