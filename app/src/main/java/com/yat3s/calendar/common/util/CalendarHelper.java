package com.yat3s.calendar.common.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;

import com.google.gson.Gson;
import com.yat3s.calendar.data.model.Day;
import com.yat3s.calendar.data.model.Event;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import static java.lang.Long.parseLong;

/**
 * Created by Yat3s on 16/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
public class CalendarHelper {
    private static final String TAG = "CalendarHelper";

    /**
     * Mocking data from system Calendar {@link Calendar}
     *
     * @return
     */
    public static List<Day> provideCalendarData() {
        List<Day> days = new ArrayList<>();
        GregorianCalendar calendar = new GregorianCalendar();
        int year = calendar.get(Calendar.YEAR);
        while (year == calendar.get(Calendar.YEAR) || year == calendar.get(Calendar.YEAR) - 1) {
            days.add(new Day(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        return days;
    }

    public static final String[] FIELDS = {
            CalendarContract.Calendars._ID,
            CalendarContract.Calendars.CALENDAR_DISPLAY_NAME,
            CalendarContract.Calendars.CALENDAR_COLOR,
            CalendarContract.Calendars.VISIBLE
    };
    public static final Uri CALENDAR_URI = Uri.parse("content://com.android.calendar/calendars");

    public static List<String> getCalendars(Context context) {
        List<String> calendars = new ArrayList<>();

        // Fetch a list of all calendars sync'd with the device and their display names
        Cursor cursor = context.getContentResolver().query(CALENDAR_URI, FIELDS, null, null, null);

        if (null == cursor) {
            return null;

        }

        try {
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String name = cursor.getString(0);
                    String displayName = cursor.getString(1);
                    // This is actually a better pattern:
                    String color = cursor.getString(cursor.getColumnIndex(CalendarContract.Calendars.CALENDAR_COLOR));
                    Boolean selected = !cursor.getString(3).equals("0");
                    calendars.add(displayName);

                    Log.d(TAG, "getCalendars: " + name);
                    Log.d(TAG, "displayName: " + displayName);
                    Log.d(TAG, "color: " + color);
                    Log.d(TAG, "selected: " + selected);
                }
            }
        } catch (AssertionError ex) {
            ex.printStackTrace();
        } finally {
            cursor.close();
        }
        return calendars;
    }

    public static List<Event> readCalendarEvent(Context context) {
        Cursor cursor = context.getContentResolver()
                .query(
                        Uri.parse("content://com.android.calendar/events"),
                        new String[]{"calendar_id", "title", "description", "allDay",
                                "dtstart", "dtend", "eventLocation", "displayColor"}, null,
                        null, null);
        if (null == cursor) {
            return null;
        }
        cursor.moveToFirst();
        List<Event> events = new ArrayList<>();
        do {
            String calendarId = cursor.getString(0);
            String title = cursor.getString(1);
            String description = cursor.getString(2);
            int allDay = Integer.parseInt(cursor.getString(3));
            long dtstart = parseLong(cursor.getString(4));
            long dtend = Long.parseLong(cursor.getString(5));
            String eventLocation = cursor.getString(6);
            int eventColor = Integer.parseInt(cursor.getString(7));
            events.add(new Event(calendarId, title, description, allDay, dtend, dtstart, eventLocation, eventColor));
        }
        while (cursor.moveToNext());
        cursor.close();
        Gson gson = new Gson();
        String s = gson.toJson(events);
        FileOutputStream outputStream;
        try {
            outputStream = new FileOutputStream("/sdcard/event/events.json");
            outputStream.write(s.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return events;
    }

    /**
     * Judge two time whether is a same day.
     * Same when year and day of year is equal.
     *
     * @param millisecond1
     * @param millisecond2
     * @return
     */
    public static boolean isSameDay(long millisecond1, long millisecond2) {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTimeInMillis(millisecond1);
        calendar2.setTimeInMillis(millisecond2);

        if (calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) &&
                calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(Calendar.DAY_OF_YEAR)) {
            return true;
        }

        return false;
    }

    public static String getHour(long millisecond) {
        return new SimpleDateFormat("hh:mm", Locale.getDefault()).format(new Date(millisecond));
    }

    public static String getInterval(long fromMillisecond, long toMillisecond) {
        long intervalMills = Math.abs(toMillisecond - fromMillisecond);
        if (intervalMills >= 24 * 60 * 60 * 1000) {
            return intervalMills / (24 * 60 * 60 * 1000) + " d";
        } else if (intervalMills >= 60 * 60 * 1000) {
            return intervalMills / (60 * 60 * 1000) + " h";
        } else {
            return intervalMills / (60 * 1000) + " m";
        }
    }
}
