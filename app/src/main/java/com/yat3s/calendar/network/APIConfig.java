package com.yat3s.calendar.network;

import com.yat3s.calendar.BuildConfig;

/**
 * Created by Yat3s on 18/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
public class APIConfig {
    public static final String PATH_LATITUDE = "latitude";
    public static final String PATH_LONGITUDE = "longitude";
    public static final String BASE_URL = "https://api.darksky.net";

    public static final String ENDPOINT_RETRIEVE_WEATHER
            = "/forecast/" + BuildConfig.WEATHER_SECRET_KEY + "/{" + PATH_LATITUDE + "},{" + PATH_LONGITUDE + "}";
}
