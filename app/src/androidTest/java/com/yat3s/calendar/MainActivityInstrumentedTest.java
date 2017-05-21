package com.yat3s.calendar;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withResourceName;
import static org.junit.Assert.assertEquals;

/**
 * Created by Yat3s on 21/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void inboxTap() {
        onView(withId(R.id.inbox_tab)).perform(click());
        onView(withId(R.id.inbox_tab)).check(matches(withResourceName("R.color.md_white_100")));
        assertEquals(mActivityRule.getActivity().getTitle(), "Inbox");
    }

    @Test
    public void calendarTap() {
        onView(withId(R.id.calendar_tab)).perform(click());
    }

    @Test
    public void fileTap() {
        onView(withId(R.id.file_tab)).perform(click());
        assertEquals(mActivityRule.getActivity().getTitle(), "Files");
    }

    @Test
    public void contactTap() {
        onView(withId(R.id.contact_tab)).perform(click());
        assertEquals(mActivityRule.getActivity().getTitle(), "People");
    }
}
