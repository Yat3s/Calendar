package com.yat3s.calendar.common.util;

import com.yat3s.calendar.Day;

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
}
