package com.yat3s.calendar;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.yat3s.calendar.agenda.AgendaView;
import com.yat3s.calendar.calendar.CalendarAdapter;
import com.yat3s.calendar.calendar.CalendarView;
import com.yat3s.calendar.common.widget.BaseAdapter;
import com.yat3s.calendar.data.DataRepository;
import com.yat3s.calendar.data.model.Day;

import java.util.List;

import butterknife.BindView;

/**
 * Created by Yat3s on 13/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
public class CalendarFragment extends BaseFragment {
    private static final String TAG = "CalendarFragment";

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
        List<Day> days = DataRepository.retrieveCalendarDateList(getActivity().getAssets());
        mAgendaView.setAgendaDataSource(days);
        mCalendarView.setCalendarDataSource(days);
        mCalendarView.updatedCurrentSelectedItem(0);

        // Be related scroll event with AgendaView and CalendarView.
        mAgendaView.setOnAgendaScrollListener(new AgendaView.OnAgendaScrollListener() {
            @Override
            public void onFirstVisibleItemPositionChanged(int position) {
                mCalendarView.updatedCurrentSelectedItem(position);
            }

            @Override
            public void onScrollStateChanged(int newState) {
                mCalendarView.fold();
            }
        });
        mCalendarView.setOnItemSelectedListener(new CalendarAdapter.OnItemSelectedListener<Day>() {
            @Override
            public void onSelected(Day day, int position) {
                mAgendaView.scrollToPosition(position);
            }
        });

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendarView.expand();
            }
        });
        mFab.setImageDrawable(new IconDrawable(getContext(), MaterialIcons.md_add)
                .colorRes(R.color.md_white_1000)
                .actionBarSize());
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden && null != getActivity()) {
            // TODO: 16/05/2017 Set current month;
            getActivity().setTitle("Calendar");
        }
    }
}
