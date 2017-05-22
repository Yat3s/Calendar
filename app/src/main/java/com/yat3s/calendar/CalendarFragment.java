package com.yat3s.calendar;

import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.yat3s.calendar.agenda.AgendaView;
import com.yat3s.calendar.calendar.CalendarAdapter;
import com.yat3s.calendar.calendar.CalendarView;
import com.yat3s.calendar.common.util.LocationHelper;
import com.yat3s.calendar.data.DataRepository;
import com.yat3s.calendar.data.model.Day;
import com.yat3s.calendar.data.source.WeatherDataSource;

import java.util.List;

import butterknife.BindView;
import rx.Observer;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Yat3s on 13/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 * <p>
 * Main calendar tab contain calendar view and agenda view.
 * <p>
 * {@link CalendarView}
 * {@link AgendaView}
 */
public class CalendarFragment extends BaseFragment {
    private static final String TAG = "CalendarFragment";

    // Mock location when location service is disable.
    private static final double MOCK_LATITUDE = 39.9042;
    private static final double MOCK_LONGITUDE = 116.4074;

    // The set including all subscriptions which user subscribed.
    private CompositeSubscription mCompositeSubscription;
    @BindView(R.id.calendar_view)
    CalendarView mCalendarView;

    @BindView(R.id.agenda_view)
    AgendaView mAgendaView;

    @BindView(R.id.fab)
    FloatingActionButton mFab;

    public static CalendarFragment newInstance() {
        Bundle args = new Bundle();

        CalendarFragment fragment = new CalendarFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentLayoutResId() {
        return R.layout.fragment_calendar;
    }

    @Override
    protected void initialize() {
        mCompositeSubscription = new CompositeSubscription();

        configureFAB();
        tieCalendarWithAgenda();
        retrieveCalendarData();

        // Retrieve weather data from target location.
        Location location = LocationHelper.getLastKnownLocation(getActivity());
        if (null == location) {
            Toast.makeText(getContext(), "Can not get location info, Made a mock location, Please check permission!",
                    Toast.LENGTH_SHORT).show();

            // Made a mock data for test.
            retrieveWeatherData(MOCK_LATITUDE, MOCK_LONGITUDE);
        } else {
            retrieveWeatherData(location.getLatitude(), location.getLongitude());
        }
    }

    /**
     * Configure floating action button for add event.
     * {@link FloatingActionButton}
     */
    private void configureFAB() {
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                App.startNewEventActivity(getContext());
            }
        });
        mFab.setImageDrawable(new IconDrawable(getContext(), MaterialIcons.md_add)
                .colorRes(R.color.md_white_1000)
                .actionBarSize());
    }

    /**
     * Tie Calendar with Agenda.
     * You can see impl {@link AgendaView#setOnAgendaScrollListener(AgendaView.OnAgendaScrollListener)}
     * and {@link CalendarView#setOnItemSelectedListener(CalendarAdapter.OnItemSelectedListener)}
     * They connect through scroll listener.
     */
    private void tieCalendarWithAgenda() {
        mAgendaView.setOnAgendaScrollListener(new AgendaView.OnAgendaScrollListener() {
            @Override
            public void onFirstVisibleItemPositionChanged(int position) {
                mCalendarView.updatedCurrentSelectedItem(position);
            }

            @Override
            public void onScrolled(int dy) {
                // FAB hide while scroll down, otherwise show.
                if (dy > 0) {
                    mFab.hide();
                } else {
                    mFab.show();
                }
            }

            @Override
            public void onScrollStateChanged(int newState) {
                mCalendarView.collapse();
                // FAB show when scroll is idle.
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    mFab.show();
                }
            }

            @Override
            public void onDisplayMonthChanged(String month) {
                getActivity().setTitle(month);
            }
        });
        mCalendarView.setOnItemSelectedListener(new CalendarAdapter.OnItemSelectedListener<Day>() {
            @Override
            public void onSelected(Day day, int position) {
                mAgendaView.scrollToPosition(position);
            }
        });

    }

    /**
     * Retrieve calendar data from data repository,
     * Note that: calendar data may from generated data or user calendar data.
     */
    private void retrieveCalendarData() {
        final List<Day> days = DataRepository.retrieveCalendarDateList(getActivity().getAssets());
        mAgendaView.setAgendaDataSource(days);
        mCalendarView.setCalendarDataSource(days);
    }

    /**
     * Retrieve weather data from data repository with target location
     *
     * @param latitude
     * @param longitude
     */
    private void retrieveWeatherData(double latitude, double longitude) {
        mCompositeSubscription.add(DataRepository.retrieveWeatherData(latitude, longitude)
                .subscribe(new Observer<WeatherDataSource>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getContext(),
                                "Oops~, Godzilla cut off the internet cable", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(WeatherDataSource weatherDataSource) {
                        mAgendaView.updateWeatherDataSource(weatherDataSource);
                        weatherDataSource.processWeatherRawData();
                    }
                }));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Cancel all subscriptions for avoid leak and crash.
        if (null != mCompositeSubscription) {
            mCompositeSubscription.unsubscribe();
        }
    }
}
