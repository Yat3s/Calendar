package com.yat3s.calendar.event;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.yat3s.calendar.R;
import com.yat3s.calendar.common.util.CalendarHelper;
import com.yat3s.calendar.data.model.Event;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yat3s.calendar.R.id.toolbar;

/**
 * Created by Yat3s on 19/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
public class EventDetailActivity extends AppCompatActivity {
    public static final String EXTRA_EVENT = "event";

    @BindView(R.id.date_tv)
    TextView mDateTv;
    @BindView(R.id.duration_tv)
    TextView mDurationTv;
    @BindView(toolbar)
    Toolbar mToolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout mAppbar;
    @BindView(R.id.main_content)
    CoordinatorLayout mMainContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        ButterKnife.bind(this);

        configureToolbar();
        setEventData();
    }

    private void setEventData() {
        Event event = (Event) getIntent().getSerializableExtra(EXTRA_EVENT);
        mCollapsingToolbar.setTitle(event.title);

        String date = new SimpleDateFormat("EEEE, MMM dd, yyyy", Locale.getDefault()).format(new Date(event.eventStart));
        mDateTv.setText(date);
        mDurationTv.setText(event.isAllDay() ? "1d" : CalendarHelper.getInterval(event.eventStart, event.eventEnd));
    }

    private void configureToolbar() {
        setSupportActionBar(mToolbar);
        if (null != getSupportActionBar()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        }
    }
}
