package com.yat3s.calendar.agenda;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.LinearLayout;

import com.yat3s.calendar.App;
import com.yat3s.calendar.R;
import com.yat3s.calendar.common.util.CalendarHelper;
import com.yat3s.calendar.common.widget.BaseAdapter;
import com.yat3s.calendar.common.widget.BaseViewHolder;
import com.yat3s.calendar.common.widget.CircleView;
import com.yat3s.calendar.common.widget.TemperatureView;
import com.yat3s.calendar.data.model.Day;
import com.yat3s.calendar.data.model.Event;
import com.yat3s.calendar.data.source.WeatherDataSource;

import java.util.List;

/**
 * Created by Yat3s on 15/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
public class AgendaAdapter extends BaseAdapter<Day> {
    private static final String TAG = "AgendaAdapter";
    private static final int TITLE_SIZE_HAS_EVENT = 14;
    private static final int TITLE_SIZE_NO_EVENT = 12;

    private WeatherDataSource mWeatherDataSource;

    public AgendaAdapter(Context context) {
        super(context);
    }

    @Override
    protected void bindDataToItemView(BaseViewHolder holder, final Day day, int position) {
        // Bind common data.
        holder.setText(R.id.date_tv, day.getDateSectionString())
                .setTextColorRes(R.id.date_tv, day.isToday ? R.color.colorPrimary : R.color.textColorGrey)
                .setBackgroundResource(R.id.date_tv, day.isToday ? R.color.blackSqueeze : R.color.md_white_1000)
                .setOnClickListener(R.id.event_layout, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (day.hasEvent()) {
                            App.startEventDetailActivity(mContext, day.getEvent());
                        } else {
                            App.startNewEventActivity(mContext);
                        }
                    }
                });

        bindWeatherItemView(holder, day);
        if (day.hasEvent()) {
            bindEventItemView(holder, day);
        } else {
            bindNoEventItemView(holder);
        }
    }

    private void bindEventItemView(BaseViewHolder holder, Day day) {
        // Just load first event for....mock.
        Event event = day.getEvents().get(0);

        // Set duration data.
        String startAt = event.isAllDay()? "ALL DAY" : CalendarHelper.getHour(event.eventStart);
        String duration = event.isAllDay() ? "1d" : CalendarHelper.getInterval(event.eventStart, event.eventEnd);
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
                .setVisible(R.id.event_duration_tv, true)
                .setTextSizeInSp(R.id.event_title_tv, TITLE_SIZE_HAS_EVENT);

        // Set location
        if (TextUtils.isEmpty(event.location)) {
            holder.setVisible(R.id.event_location_tv, false);
        } else {
            holder.setVisible(R.id.event_location_tv, true)
                    .setText(R.id.event_location_tv, mContext.getString(R.string.icon_location) + " " + event.location);
        }
    }

    private void bindNoEventItemView(BaseViewHolder holder) {
        holder.setText(R.id.event_title_tv, mContext.getString(R.string.no_event_title))
                .setTextColorRes(R.id.event_title_tv, R.color.textColorGrey)
                .setVisible(R.id.display_color_view, false)
                .setVisible(R.id.event_duration_tv, false)
                .setVisible(R.id.event_location_tv, false)
                .setTextSizeInSp(R.id.event_title_tv, TITLE_SIZE_NO_EVENT);
    }

    private void bindWeatherItemView(BaseViewHolder holder, Day day) {
        LinearLayout weatherLayout = holder.getView(R.id.weather_container_layout);
        if (null != mWeatherDataSource && mWeatherDataSource.getLatestWeather().size() > 0) {
            List<WeatherDataSource.DayWeather> dayWeathers = mWeatherDataSource.getLatestWeather();
            boolean found = false;
            for (WeatherDataSource.DayWeather dayWeather : dayWeathers) {
                if (CalendarHelper.isSameDay(dayWeather.millisecond, day.getMillisecond())) {
                    found = true;
                    TemperatureView morning = holder.getView(R.id.morning_view);
                    TemperatureView afternoon = holder.getView(R.id.afternoon_view);
                    TemperatureView evening = holder.getView(R.id.evening_view);
                    morning.setTemperatureData(dayWeather.morning);
                    afternoon.setTemperatureData(dayWeather.afternoon);
                    evening.setTemperatureData(dayWeather.evening);
                }
                if (found) {
                    weatherLayout.setVisibility(View.VISIBLE);
                    break;
                } else {
                    weatherLayout.setVisibility(View.GONE);
                }
            }

        } else {
            weatherLayout.setVisibility(View.GONE);
        }
    }

    public void updateWeatherDataSource(WeatherDataSource weatherDataSource) {
        mWeatherDataSource = weatherDataSource;
        notifyDataSetChanged();
    }

    @Override
    protected int getItemViewLayoutId(int position, Day data) {
        return R.layout.item_agenda;
    }
}
