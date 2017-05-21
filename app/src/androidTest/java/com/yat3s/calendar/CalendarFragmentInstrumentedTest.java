package com.yat3s.calendar;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.swipeDown;
import static android.support.test.espresso.action.ViewActions.swipeUp;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Yat3s on 21/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
@RunWith(AndroidJUnit4.class)
public class CalendarFragmentInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void showNewEvent() {
        onView(withId(R.id.fab)).perform(click());
    }

    @Test
    public void scrollAgenda() {
        onView(withId(R.id.agenda_view)).perform(swipeUp());
    }

    @Test
    public void scrollCalendar() {
        onView(withId(R.id.calendar_view)).perform(swipeUp());
    }

    @Test
    public void uniteScroll() {
        onView(withId(R.id.calendar_view)).perform(swipeUp());
        onView(withId(R.id.agenda_view)).perform(swipeDown());
    }

}
