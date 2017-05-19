package com.yat3s.calendar.data.model;

import android.text.TextUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by Yat3s on 14/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
public class Day {
    // The source date.
    public Date rawDate;

    // The day of month.
    public int dayOfMonth;

    // The day of year.
    public int dayOfYear;

    // The month of year.
    public int month;

    // The month abbreviation, eg. Jun/Dec
    public String monthAbbr;

    // The month full name, eg. July/December
    public String monthName;

    // The day of the week, eg. Sunday/Monday
    public String dayOfTheWeek;

    // The year.
    public int year;

    // If true, is the first day in month, eg, 01/05
    public boolean isFirstDayInMonth;

    // Is it this year.
    public boolean isThisYear;

    // Is it today.
    public boolean isToday;

    // Weather item been selected in calendar view.
    public boolean isSelected;

    // The events of this day.
    private List<Event> events;

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
    }

    public void updateEvents(List<Event> events) {
        this.events = events;
    }

    public void addEvent(Event event) {
        if (null == events) {
            events = new ArrayList<>();
        }
        events.add(event);
    }

    public List<Event> getEvents() {
        return events;
    }

    public Event getEvent() {
        if (hasEvent()) {
            // Just get first event.
            return events.get(0);
        }
        return null;
    }

    public boolean hasEvent() {
        return events != null && this.events.size() > 0;
    }

    /**
     * Generate date section string, eg. Today · Sunday, Mayday 25
     *
     * @return
     */
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
        return String.format(Locale.getDefault(), "%s%s, %s %02d",
                dateSectionPrefix, dayOfTheWeek, monthName, dayOfMonth).toUpperCase();
    }

    /**
     * @return The millisecond of {@link #rawDate}
     */
    public long getMillisecond() {
        return rawDate.getTime();
    }
}
