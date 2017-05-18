package com.yat3s.calendar.data.source;

import com.yat3s.calendar.data.model.CommonData;

import java.util.List;

/**
 * Created by Yat3s on 18/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
public class WeatherDataSource {

    public HourlyInfo hourly;

    public static class HourlyInfo {
        public String summary;

        public String icon;

        public CommonData<List<Hour>> data;
    }

    public static class Hour {
        public float temperature;

        public long time;

        public String icon;

        public String summary;
    }

}
