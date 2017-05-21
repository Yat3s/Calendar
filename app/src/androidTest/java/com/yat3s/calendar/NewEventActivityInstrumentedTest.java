package com.yat3s.calendar;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.yat3s.calendar.event.NewEventActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by Yat3s on 21/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
@RunWith(AndroidJUnit4.class)
public class NewEventActivityInstrumentedTest {
    private static final String TITLE_EVENT = "new title";
    @Rule
    public ActivityTestRule<NewEventActivity> mActivityRule = new ActivityTestRule<>(NewEventActivity.class);

    @Test
    public void testNewEvent() {
        onView(withId(R.id.title_et)).perform(typeText(TITLE_EVENT), closeSoftKeyboard());
        onView(withId(R.id.all_day_switch)).perform(click());
        onView(withId(R.id.menu_commit)).perform(click());
    }

}
