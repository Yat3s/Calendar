package com.yat3s.calendar;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add CalendarFragment to activity.
        CalendarFragment calendarFragment =
                (CalendarFragment) getSupportFragmentManager().findFragmentById(R.id.calendar_layout);
        if (null == calendarFragment) {
            calendarFragment = CalendarFragment.newInstance();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.calendar_layout, calendarFragment);
            transaction.commit();
        }
    }
}
