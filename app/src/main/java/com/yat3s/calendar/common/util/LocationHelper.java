package com.yat3s.calendar.common.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import static android.content.Context.LOCATION_SERVICE;

/**
 * Created by Yat3s on 18/05/2017.
 * Email: hawkoyates@gmail.com
 * GitHub: https://github.com/yat3s
 */
public class LocationHelper {
    private static final String TAG = "LocationHelper";
    private static final int DEFAULT_MIN_TIME = 3000; // Millis
    private static final int DEFAULT_MIN_DISTANCE = 0; // Meters
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 0x101;

    public static void registerLocation(Activity context,
                                        @Nullable final OnLocationChangedListener onLocationChangedListener) {

        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }

            @Override
            public void onLocationChanged(Location location) {
                if (null != location && null != onLocationChangedListener) {
                    onLocationChangedListener.onLocationChanged(location);
                }
            }
        };

        try {
            LocationManager locationManager = (LocationManager) context.getApplicationContext()
                    .getSystemService(LOCATION_SERVICE);

            String locationProvider = LocationManager.NETWORK_PROVIDER;
            if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                locationProvider = LocationManager.NETWORK_PROVIDER;

            } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locationProvider = LocationManager.GPS_PROVIDER;
            }
            if (locationManager.getAllProviders().contains(locationProvider)) {
                locationManager.requestLocationUpdates(locationProvider, DEFAULT_MIN_TIME,
                        DEFAULT_MIN_DISTANCE, locationListener);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Get last known location from system location service.
     * {@link LocationManager#getLastKnownLocation(String)}
     * @param context
     * @return
     */
    public static Location getLastKnownLocation(Activity context) {
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
            return null;
        }
        LocationManager locationManager = (LocationManager) context.getApplicationContext()
                .getSystemService(LOCATION_SERVICE);
        String locationProvider = LocationManager.NETWORK_PROVIDER;

        if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationProvider = LocationManager.NETWORK_PROVIDER;

        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationProvider = LocationManager.GPS_PROVIDER;
        }

        return ((LocationManager) context.getSystemService(LOCATION_SERVICE))
                .getLastKnownLocation(locationProvider);
    }

    public interface OnLocationChangedListener {
        void onLocationChanged(Location location);
    }
}
