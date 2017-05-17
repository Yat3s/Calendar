package com.yat3s.calendar.common.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.util.Log;

import com.yat3s.calendar.Day;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

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


    public static ArrayList<String> nameOfEvent = new ArrayList<String>();
    public static ArrayList<String> startDates = new ArrayList<String>();
    public static ArrayList<String> endDates = new ArrayList<String>();
    public static ArrayList<String> descriptions = new ArrayList<String>();

    public static ArrayList<String> readCalendarEvent(Context context) {
        Cursor cursor = context.getContentResolver()
                .query(
                        Uri.parse("content://com.android.calendar/events"),
                        new String[]{"calendar_id", "title", "description",
                                "dtstart", "dtend", "eventLocation"}, null,
                        null, null);
        cursor.moveToFirst();
        // fetching calendars name
        String CNames[] = new String[cursor.getCount()];

        // fetching calendars id
        nameOfEvent.clear();
        startDates.clear();
        endDates.clear();
        descriptions.clear();
        for (int i = 0; i < CNames.length; i++) {

            nameOfEvent.add(cursor.getString(1));
            startDates.add(getDate(Long.parseLong(cursor.getString(3))));
            endDates.add(getDate(Long.parseLong(cursor.getString(4))));
            descriptions.add(cursor.getString(2));
            CNames[i] = cursor.getString(1);
            Log.d(TAG, "calendarId: " + cursor.getString(0));
            Log.d(TAG, "nameOfEvent: " + cursor.getString(1));
            Log.d(TAG, "startDates: " + getDate(Long.parseLong(cursor.getString(3))));
            Log.d(TAG, "endDates: " + getDate(Long.parseLong(cursor.getString(4))));
            Log.d(TAG, "descriptions: " + cursor.getString(2));
            cursor.moveToNext();

        }
        return nameOfEvent;
    }

    public static String getDate(long milliSeconds) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}
