package com.yat3s.calendar.data.source;

import com.yat3s.calendar.data.model.Event;

import java.util.List;

/**
 * Created by Yat3s on 17/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
public class CalendarDataSource {

    public String calendarDisplayName;

    public long calendarStart;

    public long calendarEnd;

    private List<Event> events;

    public CalendarDataSource(List<Event> events) {
        this.events = events;
    }

    public List<Event> getEvents() {
        return events;
    }
}
