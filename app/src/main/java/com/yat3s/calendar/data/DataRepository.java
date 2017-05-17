package com.yat3s.calendar.data;

import android.content.res.AssetManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yat3s.calendar.common.util.AssetUtil;
import com.yat3s.calendar.data.model.Event;
import com.yat3s.calendar.data.source.CalendarDataSource;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yat3s on 17/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
public class DataRepository {

    private static final String PATH_CALENDAR_MOCK_DATA = "calendar.json";

    /**
     * Load mock data from asset json file.
     * @param assetManager {@link AssetManager}
     * @return  Contain a calendar instance
     * {@see} {@link CalendarDataSource}
     */
    public static CalendarDataSource retrieveCalendarSource(AssetManager assetManager) {
        Type listType = new TypeToken<ArrayList<Event>>() {}.getType();
        List<Event> events = new Gson().fromJson(AssetUtil.readAsset(assetManager, PATH_CALENDAR_MOCK_DATA), listType);
        return new CalendarDataSource(events);
    }
}
