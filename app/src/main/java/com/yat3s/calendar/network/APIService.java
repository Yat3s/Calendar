package com.yat3s.calendar.network;

import com.yat3s.calendar.data.source.WeatherDataSource;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Yat3s on 18/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
public interface APIService {

    @GET(APIConfig.ENDPOINT_RETRIEVE_WEATHER)
    Observable<WeatherDataSource> retrieveWeatherData(@Path(APIConfig.PATH_LATITUDE) double latitude,
                                                      @Path(APIConfig.PATH_LONGITUDE) double longitude);
}
