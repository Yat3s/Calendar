package com.yat3s.calendar.data;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.yat3s.calendar.MainActivity;
import com.yat3s.calendar.data.source.WeatherDataSource;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import rx.Observer;

import static org.junit.Assert.assertEquals;

/**
 * Created by Yat3s on 21/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
@RunWith(AndroidJUnit4.class)
public class WeatherInstrumentedTest {
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testRetrieveWeatherData() {
        DataRepository.retrieveWeatherData(39.9042, 116.4074).subscribe(new Observer<WeatherDataSource>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(WeatherDataSource weatherDataSource) {
                assertEquals(weatherDataSource.getLatestWeather().size(), 2);
            }
        });
    }
}
