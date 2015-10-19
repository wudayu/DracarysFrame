package com.nfky.yaoyijia.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.IBinder;

import com.nfky.yaoyijia.activity.MainActivity;
import com.nfky.yaoyijia.generic.Utils;

import java.lang.reflect.Method;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by David on 10/19/15.
 */
public class MockGpsService extends Service {

    Timer timer = null;
    LocationManager locationManager = null;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int flag = super.onStartCommand(intent, flags, startId);
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        Notification noti = new Notification.Builder(this)
                .setContentText("testing").setContentIntent(pendingIntent).getNotification();
        startForeground(13321, noti);

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                testMockGps();
            }
        }, 0, 1000);

        return flag;
    }

    private void testMockGps() {
        double longitude = 118.893913, latitude = 32.092321;
        // double longitude = 104.087853, latitude = 30.710432;
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        String mMockProviderName = LocationManager.GPS_PROVIDER;
        Location location = new Location(mMockProviderName);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        try {
            Method method = Location.class.getMethod("makeComplete");
            if (method != null) {
                method.invoke(location);
            }
        } catch (Exception e) {
            Utils.debug(e.toString());
        }
        locationManager.addTestProvider(mMockProviderName, false, false, false, false, false, false, false, Criteria.POWER_LOW, Criteria.ACCURACY_FINE);
        locationManager.setTestProviderEnabled(mMockProviderName, true);
        locationManager.setTestProviderLocation(mMockProviderName, location);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        if (timer != null)
            timer.cancel();
        stopForeground(true);
        super.onDestroy();
    }

}
