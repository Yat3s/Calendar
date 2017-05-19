package com.yat3s.calendar;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.MaterialModule;
import com.yat3s.calendar.event.NewEventActivity;

/**
 * Created by Yat3s on 16/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
public class App extends Application {
    private static App sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        Iconify.with(new MaterialModule());

        sInstance = this;
    }

    public Context getContext() {
        return sInstance;
    }

    public static void startNewEventActivity(Context context) {
        context.startActivity(new Intent(context, NewEventActivity.class));
    }
}
