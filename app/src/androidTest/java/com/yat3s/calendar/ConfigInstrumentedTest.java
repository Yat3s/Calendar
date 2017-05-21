package com.yat3s.calendar;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Created by Yat3s on 21/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
@RunWith(AndroidJUnit4.class)
public class ConfigInstrumentedTest {

    private Context appContext;

    @Before
    public void setup() throws Exception {
        appContext = InstrumentationRegistry.getTargetContext();
    }

    @Test
    public void testAppName() throws Exception {
        ApplicationInfo applicationInfo = appContext.getApplicationInfo();
        int stringId = applicationInfo.labelRes;
        String appName = stringId == 0 ? applicationInfo.nonLocalizedLabel.toString() : appContext.getString(stringId);
        assertEquals("Calendar", appName);
    }

    @Test
    public void testApplicationPackageName() throws Exception {
        assertEquals("com.yat3s.calendar", appContext.getPackageName());
    }

    @After
    public void release() {
        appContext = null;
    }
}
