package com.yat3s.calendar;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.yat3s.calendar.event.EventDetailActivity;

import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * Created by Yat3s on 21/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
@RunWith(AndroidJUnit4.class)

public class EventDetailActivityInstrumentedTest {
    @Rule
    public ActivityTestRule<EventDetailActivity> mActivityRule = new ActivityTestRule<>(EventDetailActivity.class);
}
