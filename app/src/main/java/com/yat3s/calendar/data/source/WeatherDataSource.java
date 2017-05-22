package com.yat3s.calendar.data.source;

import com.yat3s.calendar.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Yat3s on 18/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
public class WeatherDataSource {
    private static final String TAG = "WeatherDataSource";
    private static final int HOURS_EVERY_DAY = 24;
    private static final int MORNING_WHEN = 8;
    private static final int AFTERNOON_WHEN = 14;
    private static final int EVENING_WHEN = 20;

    private static final Map<String, Integer> ICON_MAP = new HashMap<>();
    private static final String WEATHER_ICON_CLEAR_DAY = "clear-day";
    private static final String WEATHER_ICON_CLEAR_NIGHT = "clear-night";
    private static final String WEATHER_ICON_CLOUDY = "cloudy";
    private static final String WEATHER_ICON_RAIN = "rain";
    private static final String WEATHER_ICON_SNOW = "snow";
    private static final String WEATHER_ICON_WIND = "wind";
    private static final String WEATHER_ICON_PARTLY_CLOUDY_DAY = "partly-cloudy-day";
    private static final String WEATHER_ICON_PARTLY_CLOUDY_NIGHT = "partly-cloudy-night";

    static {
        ICON_MAP.put(WEATHER_ICON_CLEAR_DAY, R.drawable.ic_clear_day_24dp);
        ICON_MAP.put(WEATHER_ICON_CLEAR_NIGHT, R.drawable.ic_clear_night_24dp);
        ICON_MAP.put(WEATHER_ICON_CLOUDY, R.drawable.ic_cloudy_24dp);
        ICON_MAP.put(WEATHER_ICON_PARTLY_CLOUDY_DAY, R.drawable.ic_partly_cloudy_day_24dp);
        ICON_MAP.put(WEATHER_ICON_PARTLY_CLOUDY_NIGHT, R.drawable.ic_partly_cloudy_night_24dp);
        ICON_MAP.put(WEATHER_ICON_RAIN, R.drawable.ic_rain_24dp);
        ICON_MAP.put(WEATHER_ICON_SNOW, R.drawable.ic_snow_24dp);
        ICON_MAP.put(WEATHER_ICON_WIND, R.drawable.ic_wind_24dp);
    }

    public HourlyInfo hourly;

    private List<DayWeather> mDayWeathers;

    public static class HourlyInfo {
        public String summary;

        public List<Hour> data;
    }

    public static class Hour {
        public float temperature;

        public long time;

        public String icon;
    }

    public static class TimeQuantum {
        private float temperature;

        private String icon;

        public int getIconId() {
            if (ICON_MAP.containsKey(icon)) {
                return ICON_MAP.get(icon);
            }
            // Default icon.
            return R.drawable.ic_clear_day_24dp;
        }

        public String getTemperature() {
            return (int) ((temperature - 32) * 5 / 9) + " ℃";
        }

        public TimeQuantum(String icon, float temperature) {
            this.icon = icon;
            this.temperature = temperature;
        }
    }

    public static class DayWeather {
        public TimeQuantum morning;

        public TimeQuantum afternoon;

        public TimeQuantum evening;

        public long millisecond;

        public DayWeather(TimeQuantum morning, TimeQuantum afternoon, TimeQuantum evening, long millisecond) {
            this.afternoon = afternoon;
            this.evening = evening;
            this.morning = morning;
            this.millisecond = millisecond;
        }
    }

    /**
     * Processing weather data including extracting all hourly data.
     */
    public void processWeatherRawData() {
        if (null == hourly || null == hourly.data || hourly.data.size() <= HOURS_EVERY_DAY) {
            return;
        }
        mDayWeathers = new ArrayList<>();
        TimeQuantum morning = null, afternoon = null, evening = null;
        for (int idx = 0; idx < hourly.data.size(); idx++) {
            Hour hour = hourly.data.get(idx);
            switch (idx % HOURS_EVERY_DAY) {
                case MORNING_WHEN:
                    morning = new TimeQuantum(hour.icon, hour.temperature);
                    break;
                case AFTERNOON_WHEN:
                    afternoon = new TimeQuantum(hour.icon, hour.temperature);
                    break;
                case EVENING_WHEN:
                    evening = new TimeQuantum(hour.icon, hour.temperature);
                    break;
            }

            // If had pass HOURS_EVERY_DAY hours, set it as a new day.
            if (idx > 0 && idx % HOURS_EVERY_DAY == 0) {
                int dayFirstIndex = ((idx - 1) / HOURS_EVERY_DAY) * HOURS_EVERY_DAY;
                mDayWeathers.add(new DayWeather(morning, afternoon, evening, hourly.data.get(dayFirstIndex).time * 1000));
            }
        }
    }

    public List<DayWeather> getLatestWeather() {
        return mDayWeathers;
    }

}
