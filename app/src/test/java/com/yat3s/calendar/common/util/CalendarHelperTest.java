package com.yat3s.calendar.common.util;

import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Created by Yat3s on 21/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
public class CalendarHelperTest {
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void getCalendars() throws Exception {
        List<Long> days = new ArrayList<>();
        GregorianCalendar calendar = new GregorianCalendar();
        int year = calendar.get(Calendar.YEAR);
        while (year == calendar.get(Calendar.YEAR)) {
            days.add(calendar.getTime().getTime());
            calendar.add(Calendar.DAY_OF_YEAR, 1);
        }
        long[] daysLong = new long[days.size()];
        for (int idx = 0; idx < days.size(); idx++) {
            daysLong[idx] = days.get(idx);
        }
        assertArrayEquals(daysLong, new long[]{10000L});
    }

    @Test
    public void isSameDay() throws Exception {
        Calendar calendar1 = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar1.setTimeInMillis(1499826400000L);
        calendar2.setTimeInMillis(1499872000000L);

        assertEquals(calendar1.get(Calendar.YEAR), calendar2.get(Calendar.YEAR));
        assertEquals(calendar1.get(Calendar.DAY_OF_YEAR), calendar2.get(Calendar.DAY_OF_YEAR));
    }

    @Test
    public void getHour() throws Exception {
        String hour = new SimpleDateFormat("hh:mm", Locale.getDefault()).format(new Date(1499126400000L));
        assertEquals(hour, "08:00");
    }

    @Test
    public void getInterval() throws Exception {
        long fromMillisecond = 1499126400000L, toMillisecond = 1499126400000L;
        long intervalMills = Math.abs(toMillisecond - fromMillisecond);
        if (intervalMills >= 24 * 60 * 60 * 1000) {
            String d = intervalMills / (24 * 60 * 60 * 1000) + " d";
            assertEquals(d, "1 d");
        } else if (intervalMills >= 60 * 60 * 1000) {
            String h = intervalMills / (60 * 60 * 1000) + " h";
            assertEquals(h, "1 h");
        } else {
            String m = intervalMills / (60 * 1000) + " m";
            assertEquals(m, "0 m");
        }
    }

}