package com.yat3s.calendar;

import android.app.Application;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.MaterialModule;

/**
 * Created by Yat3s on 16/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Iconify.with(new MaterialModule());
    }
}
