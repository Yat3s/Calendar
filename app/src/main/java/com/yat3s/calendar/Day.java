package com.yat3s.calendar;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

/**
 * Created by Yat3s on 14/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
public class Day {
    public Date rawDate;

    public int dayOfMonth;

    public int dayOfYear;

    public int month;

    public String monthAbbr;

    public String monthName;

    public String dayOfTheWeek;

    public int year;

    public boolean isFirstDayInMonth;

    public boolean isThisYear;

    public boolean isToday;

    public boolean isSelected;

    public boolean hasEvent;

    public Day(Date rawDate) {
        this.rawDate = rawDate;

        Calendar calendar = Calendar.getInstance();
        int thisYear = calendar.get(Calendar.YEAR);
        int today = calendar.get(Calendar.DAY_OF_YEAR);
        calendar.setTime(rawDate);
        if (0 == dayOfMonth) {
            dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
            isFirstDayInMonth = dayOfMonth == 1;
        }
        if (0 == month) {
            month = calendar.get(Calendar.MONTH);
        }
        if (0 == year) {
            year = calendar.get(Calendar.YEAR);
            isThisYear = thisYear == year;
        }
        if (TextUtils.isEmpty(monthAbbr)) {
            monthAbbr = new SimpleDateFormat("MMM", Locale.getDefault()).format(rawDate);
            monthName = new SimpleDateFormat("MMMM", Locale.getDefault()).format(rawDate);
        }
        if (TextUtils.isEmpty(dayOfTheWeek)) {
            dayOfTheWeek = new SimpleDateFormat("EEEE", Locale.getDefault()).format(rawDate);
        }

        isToday = isThisYear && today == calendar.get(Calendar.DAY_OF_YEAR);
        // Mock data
        hasEvent = new Random().nextBoolean();
    }

    public String getDateSectionString() {
        String dateSectionPrefix = "";
        if (isThisYear) {
            Calendar calendar = Calendar.getInstance();
            int today = calendar.get(Calendar.DAY_OF_YEAR);
            switch (today - dayOfYear) {
                case -1:
                    dateSectionPrefix = "Tomorrow · ";
                    break;
                case 0:
                    dateSectionPrefix = "Today · ";
                    break;
                case 1:
                    dateSectionPrefix = "Yesterday · ";
                    break;
            }
        }
        return String.format(Locale.getDefault(), "%s%s, %s %d",
                dateSectionPrefix, dayOfTheWeek, monthName, dayOfMonth).toUpperCase();
    }
}
