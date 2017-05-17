package com.yat3s.calendar.data;

import android.content.res.AssetManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.yat3s.calendar.common.util.AssetUtil;
import com.yat3s.calendar.common.util.CalendarHelper;
import com.yat3s.calendar.data.model.Day;
import com.yat3s.calendar.data.model.Event;
import com.yat3s.calendar.data.source.CalendarDataSource;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by Yat3s on 17/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
public class DataRepository {
    private static final String TAG = "DataRepository";
    private static final String PATH_CALENDAR_MOCK_DATA = "calendar.json";
    private static final int MONTH_LENGTH = 30;

    /**
     * Load mock data from asset json file.
     *
     * @param assetManager {@link AssetManager} for mock.
     * @return Contain a calendar instance
     * {@see} {@link CalendarDataSource}
     */
    public static CalendarDataSource retrieveCalendarSource(@NonNull AssetManager assetManager) {
        CalendarDataSource calendarDataSource =
                new Gson().fromJson(AssetUtil.readAsset(assetManager, PATH_CALENDAR_MOCK_DATA), CalendarDataSource.class);

        // Sort events by event start time.
        List<Event> events = calendarDataSource.getEvents();
        Collections.sort(events, new Comparator<Event>() {
            @Override
            public int compare(Event o1, Event o2) {
                return (int) ((o1.eventStart - o2.eventStart) / 1000);
            }
        });

        // Default load before 2 month date as calendar start,
        // load after last event start time 1 month as calendar end time;
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DAY_OF_YEAR, -MONTH_LENGTH * 2);
        calendarDataSource.calendarStart = cal.getTimeInMillis();
        cal.setTime(new Date(events.get(events.size() - 1).eventStart));
        cal.add(Calendar.DAY_OF_YEAR, MONTH_LENGTH);
        calendarDataSource.calendarEnd = cal.getTimeInMillis();

        return calendarDataSource;
    }

    /**
     * Retrieve processed date data from CalendarSource {@link CalendarDataSource}
     * <p>
     * Processing events and other data from CalendarSource,
     * {@see} {@link #retrieveCalendarSource}
     *
     * @param assetManager {@link AssetManager} for mock.
     * @return It is convenient for Adapter use and do not calc more.
     */
    public static List<Day> retrieveCalendarDateList(@NonNull AssetManager assetManager) {
        List<Day> days = new ArrayList<>();

        // Retrieve all day from calendar start to end.
        Calendar calendarStart = Calendar.getInstance();
        Calendar calendarEnd = Calendar.getInstance();
        CalendarDataSource calendarSource = retrieveCalendarSource(assetManager);
        calendarStart.setTimeInMillis(calendarSource.calendarStart);
        calendarEnd.setTimeInMillis(calendarSource.calendarEnd);
        while (calendarStart.getTimeInMillis() < calendarEnd.getTimeInMillis()) {
            days.add(new Day(calendarStart.getTime()));
            calendarStart.add(Calendar.DAY_OF_YEAR, 1);
        }

        // Processing day and events.
        // Let all event attach to day.
        List<Event> events = calendarSource.getEvents();
        int eventCursor = 0, dayCursor = 0;
        while (dayCursor < days.size() && eventCursor < events.size()) {
            Day day = days.get(dayCursor);
            Event event = events.get(eventCursor);
            if (CalendarHelper.isSameDay(day.getMillisecond(), event.eventStart)) {
                day.addEvent(event);
                eventCursor++;
            } else if (event.eventStart < day.getMillisecond()) {
                eventCursor++;
            } else {
                dayCursor++;
            }
        }

        return days;
    }
}
