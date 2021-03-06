package com.yat3s.calendar.data.model;

import android.graphics.Color;

import java.io.Serializable;

/**
 * Created by Yat3s on 16/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
public class Event implements Serializable {

    // The event belong which calendar.
    public String calendarId;

    //The title of the event.
    public String title;

    // The description of the event.
    public String description;

    // Where the event takes place.
    public String location;

    // A value of 1 indicates this event occupies the entire day,
    // as defined by the local time zone.
    // A value of 0 indicates it is a regular event that may start and end at any time during a day.
    public int allDay;

    // The time the event starts in UTC milliseconds since the epoch.
    public long eventStart;

    // The time the event ends in UTC milliseconds since the epoch
    public long eventEnd;

    // The event color.
    private int displayColor;

    public Event(String calendarId, String title, String description, int allDay, long eventEnd,
                 long eventStart, String location, int displayColor) {
        this.calendarId = calendarId;
        this.description = description;
        this.eventEnd = eventEnd;
        this.eventStart = eventStart;
        this.location = location;
        this.title = title;
        this.allDay = allDay;
        this.displayColor = displayColor;
    }

    public int getDisplayColor() {
        return Color.parseColor("#" + Integer.toHexString(-displayColor));
    }

    public boolean isAllDay() {
        return allDay == 1;
    }
}
