package com.siziksu.services.data.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.siziksu.services.app.Constants;
import com.siziksu.services.commons.Commons;
import com.siziksu.services.data.service.binder.WeatherBinder;

public class WeatherService extends Service {

    private WeatherBinder binder = new WeatherBinder();

    @Override
    public IBinder onBind(Intent intent) {
        Commons.log(Constants.TAG_WEATHER_SERVICE, Constants.SERVICE_BOUND);
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Commons.log(Constants.TAG_WEATHER_SERVICE, Constants.SERVICE_UNBOUND);
        return super.onUnbind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        binder.onCreate(this);
        Commons.log(Constants.TAG_WEATHER_SERVICE, Constants.SERVICE_CREATED);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        binder.setIntent(intent);
        Commons.log(Constants.TAG_WEATHER_SERVICE, Constants.SERVICE_STARTED);
        return START_STICKY;
    }

    @Override
    public boolean stopService(Intent name) {
        Commons.log(Constants.TAG_WEATHER_SERVICE, Constants.SERVICE_STOPPED);
        return super.stopService(name);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Commons.log(Constants.TAG_WEATHER_SERVICE, Constants.SERVICE_DESTROYED);
    }

    public void getForecast(WeatherBinder.WeatherListener listener, String string) {
        binder.getForecast(listener, string);
    }
}
