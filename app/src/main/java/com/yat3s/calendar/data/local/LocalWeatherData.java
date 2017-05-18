package com.yat3s.calendar.data.local;

import com.yat3s.calendar.data.source.WeatherDataSource;

/**
 * Created by Yat3s on 18/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
public class LocalWeatherData {

    /**
     * Retrieve local data from cache or database,
     * This is a mock operation...
     *
     * @param latitude
     * @param longitude
     * @return
     */
    public static WeatherDataSource getLocalWeatherData(double latitude, double longitude) {
        if (hasLocalWeatherData()) {
            return new WeatherDataSource();
        }
        return null;
    }

    public static boolean hasLocalWeatherData() {
        return false;
    }
}
